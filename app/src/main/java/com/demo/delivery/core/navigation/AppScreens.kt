package com.demo.delivery.core.navigation

enum class AppScreens(val route: String) {
    Profile("profile"), UserData("user_data"), Login("login"), CodeConfirm("code_confirm"),
}

sealed class CodeConfirmMethod(value: String) {
    data class Phone(val phoneNumber: String) : CodeConfirmMethod(phoneNumber)
    data class Email(val email: String) : CodeConfirmMethod(email)
}