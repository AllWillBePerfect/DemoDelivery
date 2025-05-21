package com.demo.delivery.data

sealed interface LoginAction {
    //сменить режим ввода (email/телефон)
    data object SwitchAuthMethod : LoginAction
    //изменить текст в поле ввода телефона и email
    data class UpdateUserTextPhone(val text: String) : LoginAction
    data class UpdateUserTextEmail(val text: String) : LoginAction
}