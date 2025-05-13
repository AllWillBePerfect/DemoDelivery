package com.demo.delivery.ui.screens.login.models

data class LoginState(
    val byEmailEnter: Boolean = false,
    val userTextPhone: String = "",
    val userTextEmail: String = "",
    val emailButtonEnabled: Boolean = false,
    val phoneButtonEnabled: Boolean = false,
)