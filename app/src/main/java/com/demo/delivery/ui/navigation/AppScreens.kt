package com.demo.delivery.ui.navigation

sealed class AppScreens(val route: String) {
    object Profile : AppScreens("profile")
    object UserData : AppScreens("user_data")
    object Login : AppScreens("login")
    object CodeConfirmEmail : AppScreens("code_confirm_email/{email}") {
        fun createRoute(email: String) = "code_confirm_email/$email"
    }
    object CodeConfirmPhone : AppScreens("code_confirm_phone/{phone}") {
        fun createRoute(phone: String) = "code_confirm_phone/$phone"
    }

}

sealed class CodeConfirmMethod(value: String) {
    data class Phone(val phoneNumber: String) : CodeConfirmMethod(phoneNumber)
    data class Email(val email: String) : CodeConfirmMethod(email)
}