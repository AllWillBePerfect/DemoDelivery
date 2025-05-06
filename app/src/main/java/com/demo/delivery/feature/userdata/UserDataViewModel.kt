package com.demo.delivery.feature.userdata

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.core.datastore.UserPreferences
import com.demo.delivery.core.navigation.CodeConfirmMethod
import com.demo.delivery.core.utils.Event
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

/**
 * Пункт 11.	На экране "Данные аккаунта" можно добавить
 * a.	Имя пользователя (любое, даже пустое),
 * b.	почту(если регистрация была по номеру) или номер(если регистрация была по почте),
 * c.	дату рождения, которую тоже нужно форматировать(задача со звездочкой*: проверка корректного набора: пользователю не меньше 18 лет),
 * d.	Адрес доставки, квартира(максимум 20символов), домофон(максимум 20символов) - любой текст,
 * e.	Подъезд(максимум 20символов) и этаж(максимум 20символов).
 */

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val userPreferences: UserPreferences
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
            userPreferences.userDataFlow.collect { userData ->
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

    fun dispatchAction(action: UserDataAction) {
        when (action) {
            // Пункт 11.	На экране "Данные аккаунта" можно добавить
            //  b.	почту(если регистрация была по номеру) или номер(если регистрация была по почте),
            //
            is UserDataAction.UpdateUserTextPhone -> {
                val errorText =
                    if (action.phone.length == 10 || action.phone.isEmpty()) null else "Неполный номер"
                _state.value = _state.value?.copy(
                    userTextPhone = action.phone,
                    userTextPhoneError = errorText
                )
                checkForChanges()
            }

            // Пункт 11.	На экране "Данные аккаунта" можно добавить
            //  b.	почту(если регистрация была по номеру) или номер(если регистрация была по почте),
            //
            is UserDataAction.UpdateUserTextEmail -> {
                val errorText = isEmailCorrect(action.email)
                _state.value = _state.value?.copy(
                    userTextEmail = action.email,
                    userTextEmailError = errorText
                )
                checkForChanges()
            }

            //Пункт 11.	На экране "Данные аккаунта" можно добавить
            //      a.	Имя пользователя (любое, даже пустое),
            is UserDataAction.UpdateUserTextName -> {
                _state.value = _state.value?.copy(userTextName = action.name)
                checkForChanges()
            }

            //Пункт 11.	На экране "Данные аккаунта" можно добавить
            //  c.	дату рождения, которую тоже нужно форматировать(проверка корректного набора: пользователю не меньше 18 лет),
            is UserDataAction.UpdateUserTextBirthday -> {
                val isAdult = if (action.birthday.isEmpty()) true else isAdult(action.birthday)
                val errorText = if (!isAdult) "Пользователю еще нет 18 лет" else null
                _state.value = _state.value?.copy(
                    userTextBirthday = action.birthday,
                    userTextBirthdayError = errorText
                )

                checkForChanges()
            }


            //d.	Адрес доставки
            is UserDataAction.UpdateUserTextAddress -> {
                _state.value = _state.value?.copy(userTextAddress = action.address)
                checkForChanges()
            }

            //d.	квартира(максимум 20символов)
            is UserDataAction.UpdateUserTextApartment -> {
                _state.value = _state.value?.copy(userTextApartment = action.apartment)
                checkForChanges()
            }

            //d.    подъезд(максимум 20символов)
            is UserDataAction.UpdateUserTextEntrance -> {
                _state.value = _state.value?.copy(userTextEntrance = action.entrance)
                checkForChanges()
            }

            //d.    этаж(максимум 20символов)
            is UserDataAction.UpdateUserTextFloor -> {
                _state.value = _state.value?.copy(userTextFloor = action.floor)
                checkForChanges()
            }

            //d.    домофон(максимум 20символов)
            is UserDataAction.UpdateUserTextIntercom -> {
                _state.value = _state.value?.copy(userTextIntercom = action.intercom)
                checkForChanges()
            }

            UserDataAction.SwitchToChangeMode -> {
                val currentState = _state.value ?: return
                _state.value = currentState.copy(
                    emailTextInputEnabled = currentState.userTextEmail.isEmpty(),
                    phoneTextInputEnabled = currentState.userTextPhone.isEmpty(),
                    isChangeMode = true
                )
            }

            UserDataAction.LaunchDeleteDialog -> {
                _state.value = _state.value?.copy(isDeleteDialogVisible = true)
            }

            UserDataAction.LaunchExitDialog -> {
                _state.value = _state.value?.copy(isExitDialogVisible = true)
            }

            UserDataAction.CloseDialog -> {
                _state.value = _state.value?.copy(
                    isDeleteDialogVisible = false,
                    isExitDialogVisible = false
                )
            }
            //Пункт 14.	При удалении и выходе из аккаунта должна
            // происходить очистка всех данных и переход на экран "Профиль".
            UserDataAction.ConfirmDeleteDialog -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        deleteUser()
                    }
                    _state.value = _state.value?.copy(isDeleteDialogVisible = false)
                    _closeScreenEffect.value = Event(Unit)
                }
            }

            //Пункт 14.	При удалении и выходе из аккаунта должна
            // происходить очистка всех данных и переход на экран "Профиль".
            UserDataAction.ConfirmExitDialog -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        deleteUser()
                    }
                    _state.value = _state.value?.copy(isExitDialogVisible = false)
                    _closeScreenEffect.value = Event(Unit)
                }
            }

            UserDataAction.SaveChanges -> {
                viewModelScope.launch {
                    saveChanges()
                }
            }

            is UserDataAction.ChooseDate -> {
                viewModelScope.launch {
                    val resultDate = withContext(Dispatchers.IO) {
                        parseLongToDate(action.date)
                    }
                    dispatchAction(UserDataAction.UpdateUserTextBirthday(resultDate))
                    _state.value = _state.value?.copy(isDatePickerVisible = false)
                    checkForChanges()

                }
            }

            UserDataAction.CloseDataPicker -> {
                _state.value = _state.value?.copy(isDatePickerVisible = false)
            }

            UserDataAction.LaunchDataPicker -> {
                _state.value = _state.value?.copy(isDatePickerVisible = true)
            }
        }
    }


    private suspend fun deleteUser() = userPreferences.deleteUserData()


    /**
     * Если все условия выполнены, меняем флаг состояния.
     */
    private fun checkForChanges() {

        val currentState = _state.value ?: return
        val originalData = runBlocking {
            userPreferences.userDataFlow.first()
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

        // проверяем, что поле с датой заполнено корректно
        val dateNumber =
            currentState.userTextBirthday.length == 8 || currentState.userTextBirthday.isEmpty()


        // проверяет, что нет ошибок
        val hasErrors = currentState.userTextBirthdayError != null
                || currentState.userTextPhoneError != null
                || currentState.userTextEmailError != null

        _state.value = currentState.copy(isSaveChanges = hasChanges && dateNumber && !hasErrors)
    }

    /**
     * Сохранить данные в бд
     */
    private suspend fun saveChanges() {

        val state = _state.value ?: return
        val originalData = runBlocking {
            userPreferences.userDataFlow.first()
        }

        // Сохраняем в бд
        withContext(Dispatchers.IO) {
            userPreferences.saveUserData(
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

        // отключаем режим редактирования и блокируем email и phone текстовые поля
        _state.value = state.copy(
            isChangeMode = false,
            emailTextInputEnabled = false,
            phoneTextInputEnabled = false,
        )
    }


    /**
     * Проверяет, является ли пользователь совершеннолетним
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
     * дата приходит в формате long, нужно преобразовать в строку
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
     * валидация email
     */
    private fun isEmailCorrect(text: String): String? {
        val correctEmail = if (text.contains('@')) {
            val beforePart = text.substringBefore('@')
            Patterns.EMAIL_ADDRESS.matcher(text).matches() && beforePart.last() != '.'
        } else {
            Patterns.EMAIL_ADDRESS.matcher(text).matches()

        }
        return if (correctEmail || text.isEmpty()) null else "Неверный формат email"
    }
}

data class UserDataState(
    val isChangeMode: Boolean = false,
    val phoneTextInputEnabled: Boolean = false,
    val emailTextInputEnabled: Boolean = false,
    val userTextPhone: String = "",
    val userTextPhoneError: String? = null,
    val userTextEmail: String = "",
    val userTextEmailError: String? = null,
    val userTextName: String = "",
    val userTextBirthday: String = "",
    val userTextBirthdayError: String? = null,
    val userTextAddress: String = "",
    val userTextApartment: String = "",
    val userTextEntrance: String = "",
    val userTextFloor: String = "",
    val userTextIntercom: String = "",
    val isSaveChanges: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isExitDialogVisible: Boolean = false,
    val isDatePickerVisible: Boolean = false,
)

sealed interface UserDataAction {

    // обновление текстовых полей
    data class UpdateUserTextPhone(val phone: String) : UserDataAction
    data class UpdateUserTextEmail(val email: String) : UserDataAction
    data class UpdateUserTextName(val name: String) : UserDataAction
    data class UpdateUserTextBirthday(val birthday: String) : UserDataAction
    data class UpdateUserTextAddress(val address: String) : UserDataAction
    data class UpdateUserTextApartment(val apartment: String) : UserDataAction
    data class UpdateUserTextEntrance(val entrance: String) : UserDataAction
    data class UpdateUserTextFloor(val floor: String) : UserDataAction
    data class UpdateUserTextIntercom(val intercom: String) : UserDataAction

    // меняет режим редактирования
    data object SwitchToChangeMode : UserDataAction

    // запуск диалоговых окон и действия с ними
    data object LaunchDeleteDialog : UserDataAction
    data object LaunchExitDialog : UserDataAction
    data object CloseDialog : UserDataAction
    data object ConfirmDeleteDialog : UserDataAction
    data object ConfirmExitDialog : UserDataAction

    //запуск view для выбора даты
    data object LaunchDataPicker : UserDataAction
    data object CloseDataPicker : UserDataAction
    data class ChooseDate(val date: Long) : UserDataAction

    // сохранение изменений
    data object SaveChanges : UserDataAction
}