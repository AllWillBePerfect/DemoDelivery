package com.demo.delivery.data

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