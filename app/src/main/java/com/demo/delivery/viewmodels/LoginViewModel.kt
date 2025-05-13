package com.demo.delivery.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.delivery.ui.screens.login.models.LoginAction
import com.demo.delivery.ui.screens.login.models.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableLiveData<LoginState>(LoginState())
    val state: LiveData<LoginState> = _state

    fun dispatchAction(action: LoginAction) {
        when (action) {
            is LoginAction.SwitchAuthMethod -> switchAuthMethod()
            is LoginAction.UpdateUserTextEmail -> setUserTextEmail(action.text)
            is LoginAction.UpdateUserTextPhone -> {
                setUserTextPhone(action.text)
            }

            LoginAction.OnEmailButtonClick -> {}
            LoginAction.OnPhoneButtonClick -> {}
        }
    }

    /**
     * Войти по email или по номеру телефона
     */
    private fun switchAuthMethod() {
        _state.value?.let {
            _state.value = it.copy(byEmailEnter = !it.byEmailEnter)
        }
    }

    /**
     * Функция проверяет корректность введенного номера телефона
     */
    private fun setUserTextPhone(text: String) {
        val isPhoneValid = text.length == 10
        _state.value = _state.value?.copy(
            phoneButtonEnabled = isPhoneValid,
            userTextPhone = text
        )
    }

    /**
     * Пункт 4.	Для почты корректными являются test@test.test и test.test@test.test,
     * НЕкорректными - test.@test.test и test.test.@test.test;
     * Функция проверяет корректность введенного email
     */
    private fun setUserTextEmail(text: String) {
        val correctEmail = if (text.contains('@')) {
            val beforePart = text.substringBefore('@')
            // проверка на отсутствия точки перед @
            Patterns.EMAIL_ADDRESS.matcher(text).matches() && beforePart.last() != '.'
        } else {
            Patterns.EMAIL_ADDRESS.matcher(text).matches()

        }
        _state.value = _state.value?.copy(emailButtonEnabled = correctEmail, userTextEmail = text)
    }
}



