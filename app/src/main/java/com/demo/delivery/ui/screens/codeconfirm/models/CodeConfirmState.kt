package com.demo.delivery.ui.screens.codeconfirm.models

data class CodeConfirmState(
    val byEmailEnter: Boolean = false,
    val buttonEnabled: Boolean = false,
    val isError: Boolean = false,
    val timerValue: Int? = null,
    val otpValue: String = "",
)