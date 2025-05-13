package com.demo.delivery.refactoring.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.refactoring.utils.UserPreferencesUtils
import com.demo.delivery.refactoring.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeConfirmViewModel @Inject constructor(
    private val userPreferencesUtils: UserPreferencesUtils
) : ViewModel() {

    private val _state = MutableLiveData(CodeConfirmState())
    val state: LiveData<CodeConfirmState> = _state

    private val _navigateToProfileEffect = MutableLiveData<Event<Unit>>()
    val navigateToProfileEffect: LiveData<Event<Unit>> = _navigateToProfileEffect


    fun dispatchAction(action: CodeConfirmAction) {
        when (action) {
            is CodeConfirmAction.InputCode -> {
                val validCode = action.code == codeConstant
                if (validCode) {
                    _state.value = _state.value?.copy(
                        buttonEnabled = true,
                        isError = false
                    )
                } else {
                    _state.value = _state.value?.copy(
                        buttonEnabled = false,
                        isError = true
                    )
                }
            }


            CodeConfirmAction.CleanCode -> {
                _state.value = _state.value?.copy(
                    isError = true
                )
            }

            CodeConfirmAction.HideError -> {
                _state.value = _state.value?.copy(
                    isError = false
                )
            }

            is CodeConfirmAction.StartTimer -> {
                startTimer(action.initialValue)
            }

            is CodeConfirmAction.AuthByEmail -> {
                viewModelScope.launch {
                    userPreferencesUtils.authorizeByEmail(action.email)
                    _navigateToProfileEffect.value = Event(Unit)
                }
            }
            is CodeConfirmAction.AuthByPhone -> {
                viewModelScope.launch {
                    userPreferencesUtils.authorizeByPhone(action.phone)
                    _navigateToProfileEffect.value = Event(Unit)
                }
            }
        }
    }


    /**
     * Запуск таймера. Если таймер завершился, `timerValue == null`
     */
    fun startTimer(initialValue: Int) {
        viewModelScope.launch {
            var currentValue = initialValue
            while (currentValue > 0) {
                _state.value = _state.value?.copy(timerValue = currentValue)
                delay(1000L)
                currentValue--
            }
            _state.value = _state.value?.copy(timerValue = null)
        }
    }


    companion object {
        //Пункт 6.	Код можно константы(например 1234)
        // как поле viewModel-класса и с ним набранное пользователем сравнивать.
        // Приложение не обращается к API;
        const val codeConstant = "1234"
    }
}

data class CodeConfirmState(
    val byEmailEnter: Boolean = false,
    val buttonEnabled: Boolean = false,
    val isError: Boolean = false,
    val timerValue: Int? = null,
    val otpValues: List<String> = List(4) { "" }
)

sealed interface CodeConfirmAction {
    // ввели 4-значный код
    data class InputCode(val code: String) : CodeConfirmAction
    // неверный код, нужно показать ошибку
    data object CleanCode : CodeConfirmAction
    // скрыть ошибку
    data object HideError : CodeConfirmAction
    // старт таймера
    data class StartTimer(val initialValue: Int) : CodeConfirmAction
    // авторизация через email
    data class AuthByEmail(val email: String) : CodeConfirmAction
    // авторизация через телефон
    data class AuthByPhone(val phone: String): CodeConfirmAction
}