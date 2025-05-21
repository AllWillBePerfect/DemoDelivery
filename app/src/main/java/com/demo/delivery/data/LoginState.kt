package com.demo.delivery.data

import com.demo.delivery.ui.screens.login.LoginScreen

/**
 * Состояние экрана ввода номера телефона или email для авторизации ([LoginScreen]).
 *
 * @property byEmailEnter флаг, указывающий, используется ли авторизация по email.
 * @property userTextPhone текст, введенный пользователем в поле для номера телефона.
 * @property userTextEmail текст, введенный пользователем в поле для email.
 * @property emailButtonEnabled флаг, указывающий, активна ли кнопка авторизации по email.
 * @property phoneButtonEnabled флаг, указывающий, активна ли кнопка авторизации по номеру телефона.
 */
data class LoginState(
    val byEmailEnter: Boolean = false,
    val userTextPhone: String = "",
    val userTextEmail: String = "",
    val emailButtonEnabled: Boolean = false,
    val phoneButtonEnabled: Boolean = false,
)