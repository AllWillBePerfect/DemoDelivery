package com.demo.delivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.data.UserDataAction
import com.demo.delivery.data.UserDataState
import com.demo.delivery.ui.navigation.CodeConfirmMethod
import com.demo.delivery.utils.Event
import com.demo.delivery.utils.TextValidationUtils
import com.demo.delivery.utils.UserPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject


/* * Пункт 11.	На экране "Данные аккаунта" можно добавить
 * a.	Имя пользователя (любое, даже пустое),
 * b.	почту(если регистрация была по номеру) или номер(если регистрация была по почте),
 * c.	дату рождения, которую тоже нужно форматировать(задача со звездочкой*: проверка корректного набора: пользователю не меньше 18 лет),
 * d.	Адрес доставки, квартира(максимум 20символов), домофон(максимум 20символов) - любой текст,
 * e.	Подъезд(максимум 20символов) и этаж(максимум 20символов).
 */

/**
 * ViewModel для экрана редактирования данных пользователя.
 *
 * @property userPreferencesUtils класс для работы с данными пользователя.
 */
@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val userPreferencesUtils: UserPreferencesUtils
) : ViewModel() {

    private val _state = MutableLiveData<UserDataState>(UserDataState())
    val state: LiveData<UserDataState> = _state

    private val _closeScreenEffect = MutableLiveData<Event<Unit>>()
    val closeScreenEffect: LiveData<Event<Unit>> = _closeScreenEffect

    private val _navigateToCodeConfirmEffect = MutableLiveData<Event<CodeConfirmMethod>>()
    val navigateToCodeConfirmEffect: LiveData<Event<CodeConfirmMethod>> =
        _navigateToCodeConfirmEffect


    init {
        viewModelScope.launch {
            userPreferencesUtils.userDataFlow.collect { userData ->
                _state.value = _state.value?.copy(
                    userTextName = userData.name,
                    userTextPhone = userData.phone,
                    userTextEmail = userData.email,
                    userTextBirthday = userData.birthday,
                    userTextAddress = userData.address,
                    userTextApartment = userData.apartment,
                    userTextEntrance = userData.entrance,
                    userTextFloor = userData.floor,
                    userTextIntercom = userData.intercom
                )
            }
        }
    }

    /**
     * Обрабатывает действия пользователя и обновляет состояние.
     *
     * @param action действие, которое нужно обработать.
     */
    fun dispatchAction(action: UserDataAction) {
            when (action) {
                // Пункт 11.	На экране "Данные аккаунта" можно добавить
                //  b.	почту(если регистрация была по номеру) или номер(если регистрация была по почте),
                //
                is UserDataAction.UpdateUserTextPhone -> {
                    val currentState = _state.value?: return
                    val errorText =
                        if (TextValidationUtils.validatePhoneNumber(action.phone) || action.phone.isEmpty()) null else "Неполный номер"
                    val updatedState = currentState.copy(
                        userTextPhone = action.phone,
                        userTextPhoneError = errorText
                    )
                    checkForChanges(updatedState)
                }

                // Пункт 11.	На экране "Данные аккаунта" можно добавить
                //  b.	почту(если регистрация была по номеру) или номер(если регистрация была по почте),
                //
                is UserDataAction.UpdateUserTextEmail -> {
                    val currentState = _state.value?: return
                    val errorText = isEmailCorrect(action.email)
                    val updatedState = currentState.copy(
                        userTextEmail = action.email,
                        userTextEmailError = errorText
                    )
                    checkForChanges(updatedState)
                }

                //Пункт 11.	На экране "Данные аккаунта" можно добавить
                //      a.	Имя пользователя (любое, даже пустое),
                is UserDataAction.UpdateUserTextName -> {
                    val currentState = _state.value?: return
                    val updatedState = currentState.copy(userTextName = action.name)
                    checkForChanges(updatedState)
                }

                //Пункт 11.	На экране "Данные аккаунта" можно добавить
                //  c.	дату рождения, которую тоже нужно форматировать(проверка корректного набора: пользователю не меньше 18 лет),
                is UserDataAction.UpdateUserTextBirthday -> {
                    val currentState = _state.value?: return
                    val isAdult = if (action.birthday.isEmpty()) true else isAdult(action.birthday)
                    val errorText = when {
                        action.birthday.length <= 7 && action.birthday.isNotEmpty() -> "Неполный формат даты"
                        !isAdult -> "Пользователю еще нет 18 лет"
                        else -> null
                    }
                    val updatedState = currentState.copy(
                        userTextBirthday = action.birthday,
                        userTextBirthdayError = errorText
                    )
                    checkForChanges(updatedState)
                }


                //d.	Адрес доставки
                is UserDataAction.UpdateUserTextAddress -> {
                    val currentState = _state.value?: return
                    val updatedState = currentState.copy(userTextAddress = action.address)
                    checkForChanges(updatedState)
                }

                //d.	квартира(максимум 20символов)
                is UserDataAction.UpdateUserTextApartment -> {
                    val currentState = _state.value?: return
                    val updatedState = currentState.copy(userTextApartment = action.apartment)
                    checkForChanges(updatedState)
                }

                //d.    подъезд(максимум 20символов)
                is UserDataAction.UpdateUserTextEntrance -> {
                    val currentState = _state.value ?: return
                    val updatedState = currentState.copy(userTextEntrance = action.entrance)
                    checkForChanges(updatedState)
                }

                //d.    этаж(максимум 20символов)
                is UserDataAction.UpdateUserTextFloor -> {
                    val currentState = _state.value ?: return
                    val updatedState = currentState.copy(userTextFloor = action.floor)
                    checkForChanges(updatedState)
                }

                //d.    домофон(максимум 20символов)
                is UserDataAction.UpdateUserTextIntercom -> {
                    val currentState = _state.value ?: return
                    val updatedState = currentState.copy(userTextIntercom = action.intercom)
                    checkForChanges(updatedState)
                }

                UserDataAction.SwitchToChangeMode -> {
                    val currentState = _state.value ?: return
                    _state.postValue(currentState.copy(
                        emailTextInputEnabled = currentState.userTextEmail.isEmpty(),
                        phoneTextInputEnabled = currentState.userTextPhone.isEmpty(),
                        isChangeMode = true
                    ))
                }

                UserDataAction.LaunchDeleteDialog -> {
                    _state.postValue(_state.value?.copy(isDeleteDialogVisible = true))
                }

                UserDataAction.LaunchExitDialog -> {
                    _state.postValue(_state.value?.copy(isExitDialogVisible = true))
                }

                UserDataAction.CloseDialog -> {
                    _state.postValue(_state.value?.copy(
                        isDeleteDialogVisible = false,
                        isExitDialogVisible = false
                    ))
                }
                //Пункт 14.	При удалении и выходе из аккаунта должна
                // происходить очистка всех данных и переход на экран "Профиль".
                UserDataAction.ConfirmDeleteDialog -> {
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            deleteUser()
                        }
                        _state.postValue(_state.value?.copy(isDeleteDialogVisible = false))
                        _closeScreenEffect.postValue(Event(Unit))
                    }
                }

                //Пункт 14.	При удалении и выходе из аккаунта должна
                // происходить очистка всех данных и переход на экран "Профиль".
                UserDataAction.ConfirmExitDialog -> {
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            deleteUser()
                        }
                        _state.postValue(_state.value?.copy(isExitDialogVisible = false))
                        _closeScreenEffect.postValue(Event(Unit))
                    }
                }

                UserDataAction.SaveChanges -> {
                    viewModelScope.launch {
                        saveChanges()
                    }
                }

                is UserDataAction.ChooseDate -> {
                    viewModelScope.launch {
                        val currentState = _state.value?: return@launch
                        val resultDate = withContext(Dispatchers.IO) {
                            parseLongToDate(action.date)
                        }
                        dispatchAction(UserDataAction.UpdateUserTextBirthday(resultDate))
                        val updatedState = currentState.copy(isDatePickerVisible = false)
                        checkForChanges(updatedState)

                    }
                }

                UserDataAction.CloseDataPicker -> {
                    _state.postValue(_state.value?.copy(isDatePickerVisible = false))
                }

                UserDataAction.LaunchDataPicker -> {
                    _state.postValue(_state.value?.copy(isDatePickerVisible = true))
                }
            }
    }

    /**
     * Удаляет данные пользователя.
     */
    private suspend fun deleteUser() = userPreferencesUtils.deleteUserData()


    /**
     * Проверяет наличие изменений в данных пользователя и обновляет состояние.
     */
    private fun checkForChanges(currentState: UserDataState) {

        val originalData = runBlocking {
            userPreferencesUtils.userDataFlow.first()
        }

        // Проверяем наличие изменений в полях
        val hasChanges = currentState.userTextPhone != originalData.phone ||
                currentState.userTextEmail != originalData.email ||
                currentState.userTextName != originalData.name ||
                currentState.userTextBirthday != originalData.birthday ||
                currentState.userTextAddress != originalData.address ||
                currentState.userTextApartment != originalData.apartment ||
                currentState.userTextEntrance != originalData.entrance ||
                currentState.userTextFloor != originalData.floor ||
                currentState.userTextIntercom != originalData.intercom


        // проверяет, что нет ошибок в валидации поля
        val hasErrors = currentState.userTextBirthdayError != null
                || currentState.userTextPhoneError != null
                || currentState.userTextEmailError != null

        _state.value = currentState.copy(isSaveChanges = hasChanges && !hasErrors)
    }

    /**
     * Сохраняет изменения в datastore.
     */
    private suspend fun saveChanges() {

        val state = _state.value ?: return
        val originalData = runBlocking {
            userPreferencesUtils.userDataFlow.first()
        }

        // Сохраняем в datastore
        withContext(Dispatchers.IO) {
            userPreferencesUtils.saveUserData(
                name = state.userTextName,
                birthday = state.userTextBirthday,
                address = state.userTextAddress,
                apartment = state.userTextApartment,
                entrance = state.userTextEntrance,
                floor = state.userTextFloor,
                intercom = state.userTextIntercom
            )
        }

        // если был изменен номер телефона, то отправляем навигацию с параметрами телефона
        if (state.userTextPhone != originalData.phone) {
            _navigateToCodeConfirmEffect.value = Event(CodeConfirmMethod.Phone(state.userTextPhone))

        }
        // если был изменен email, то отправляем навигацию с параметрами email
        if (state.userTextEmail != originalData.email) {
            _navigateToCodeConfirmEffect.value = Event(CodeConfirmMethod.Email(state.userTextEmail))

        }

        // отключаем режим редактирования, а также блокируем текстовые поля email и phone
        _state.value = state.copy(
            isChangeMode = false,
            emailTextInputEnabled = false,
            phoneTextInputEnabled = false,
        )
    }


    /**
     * Проверяет, является ли пользователь совершеннолетним.
     *
     * @param birthDateRaw строка с датой рождения в формате "ddMMyyyy".
     * @return true, если пользователю 18 лет или больше, иначе false.
     */
    fun isAdult(birthDateRaw: String): Boolean {
        return try {

            // Формат даты "ddMMyyyy"
            val formatter = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
            val birthDate = formatter.parse(birthDateRaw)

            // Получаем текущую дату
            val today = Calendar.getInstance()

            // Вычисляем возраст
            val birthCalendar = Calendar.getInstance()
            birthCalendar.time = birthDate
            val age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

            // Проверка, если день рождения еще не прошел в этом году
            val isBeforeBirthdayThisYear =
                today.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                        (today.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                                today.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))

            val finalAge = if (isBeforeBirthdayThisYear) age - 1 else age

            finalAge >= 18
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Преобразует дату в миллисекундах в строку формата "ddMMyyyy".
     *
     * @param dateInMillis дата в миллисекундах.
     * @return строка с датой или сообщение об ошибке.
     */
    private suspend fun parseLongToDate(dateInMillis: Long?): String {
        return if (dateInMillis != null) {
            val date = Date(dateInMillis)

            // Форматируем дату в строку
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormat.format(date).replace("/", "")
        } else {
            "Неверный формат даты"
        }
    }

    /**
     * Проверяет корректность формата email.
     *
     * @param text строка с email.
     * @return null, если email корректен или пуст, иначе сообщение об ошибке.
     */
    private fun isEmailCorrect(text: String): String? {
        val correctEmail = TextValidationUtils.validateEmail(text)
        return if (correctEmail || text.isEmpty()) null else "Неверный формат email"
    }
}



