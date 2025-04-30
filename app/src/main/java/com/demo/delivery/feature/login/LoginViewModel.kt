package com.demo.delivery.feature.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<LoginState>(LoginState())
    val state: LiveData<LoginState> = _state

    fun dispatchAction(action: LoginAction) {
        when (action) {
            is LoginAction.SwitchAuthMethod -> switchAuthMethod()
            is LoginAction.UpdateUserTextEmail -> setUserTextEmail(action.text)
            is LoginAction.UpdateUserTextPhone -> {
                setUserTextPhone(action.text)
            }
        }
    }

    private fun switchAuthMethod() {
        _state.value?.let {
            _state.value = it.copy(byEmailEnter = !it.byEmailEnter)
        }
    }

    private fun setUserTextPhone(text: String) {
        val resultString = if (text.length >= 10) {
            _state.value = _state.value?.copy(phoneButtonEnabled = true)
            // ограничение по длине номера телефона, если оно больше 10 символов, оставляем только первые 10 символов
            text.substring(0..9)
        } else {
            _state.value = _state.value?.copy(phoneButtonEnabled = false)
            text
        }
        _state.value = _state.value?.copy(userTextPhone = resultString)
    }

    private fun setUserTextEmail(text: String) {
        val correctEmail = if (text.contains('@')) {
            val beforePart = text.substringBefore('@')
            Patterns.EMAIL_ADDRESS.matcher(text).matches() && beforePart.last() != '.'
        } else {
            Patterns.EMAIL_ADDRESS.matcher(text).matches()

        }
        _state.value = _state.value?.copy(emailButtonEnabled = correctEmail)
        _state.value = _state.value?.copy(userTextEmail = text)

    }
}

data class LoginState(
    val byEmailEnter: Boolean = false,
    val userTextPhone: String = "",
    val userTextEmail: String = "",
    val emailButtonEnabled: Boolean = false,
    val phoneButtonEnabled: Boolean = false,
)

sealed interface LoginAction {
    data object SwitchAuthMethod : LoginAction
    data class UpdateUserTextPhone(val text: String) : LoginAction
    data class UpdateUserTextEmail(val text: String) : LoginAction
}