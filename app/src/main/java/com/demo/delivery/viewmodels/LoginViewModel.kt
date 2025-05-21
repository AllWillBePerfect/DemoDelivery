package com.demo.delivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.delivery.data.LoginAction
import com.demo.delivery.data.LoginState
import com.demo.delivery.utils.TextValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel для экрана ввода номера телефона или email для авторизации.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    /**
     * Текущее состояние экрана LoginScreen
     */
    private val _state = MutableLiveData<LoginState>(LoginState())
    val state: LiveData<LoginState> = _state

    fun dispatchAction(action: LoginAction) {
        when (action) {
            is LoginAction.SwitchAuthMethod -> switchAuthMethod()
            is LoginAction.UpdateUserTextEmail -> setUserTextEmail(action.text)
            is LoginAction.UpdateUserTextPhone -> setUserTextPhone(action.text)

        }
    }

    /**
     * Войти по email или по номеру телефона
     */
    private fun switchAuthMethod() {
        _state.value?.let {
            _state.postValue(it.copy(byEmailEnter = !it.byEmailEnter))
        }
    }

    /**
     * Функция проверяет корректность введенного номера телефона
     */
    private fun setUserTextPhone(phone: String) {
        val isPhoneValid = TextValidationUtils.validatePhoneNumber(phone)
        _state.value = _state.value?.copy(
            phoneButtonEnabled = isPhoneValid,
            userTextPhone = phone
        )
    }

    /**
     * Пункт 4.	Для почты корректными являются test@test.test и test.test@test.test,
     * НЕкорректными - test.@test.test и test.test.@test.test;
     * Функция проверяет корректность введенного email
     */
    private fun setUserTextEmail(email: String) {
        val correctEmail = TextValidationUtils.validateEmail(email)
        _state.value = _state.value?.copy(emailButtonEnabled = correctEmail, userTextEmail = email)
    }
}



