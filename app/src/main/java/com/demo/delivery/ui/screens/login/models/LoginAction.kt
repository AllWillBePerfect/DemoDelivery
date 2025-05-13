package com.demo.delivery.ui.screens.login.models

sealed interface LoginAction {
    //сменить режим ввода (email/телефон)
    data object SwitchAuthMethod : LoginAction
    //изменить текст в поле ввода телефона и email
    data class UpdateUserTextPhone(val text: String) : LoginAction
    data class UpdateUserTextEmail(val text: String) : LoginAction

    // нажатие на кнопку
    data object OnEmailButtonClick : LoginAction
    data object OnPhoneButtonClick : LoginAction
}