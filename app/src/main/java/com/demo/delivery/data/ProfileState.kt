package com.demo.delivery.data

import com.demo.delivery.ui.screens.profile.ProfileScreen
import com.demo.delivery.ui.screens.userdata.UserDataScreen

/**
 * Состояние экрана информации о пользователе ([ProfileScreen]).
 *
 * @property isLoggedIn флаг, указывающий, авторизовался ли пользователь.
 * @property userName текст, имя пользователя, которое он указал в [UserDataScreen].
 */
data class ProfileState(
    val isLoggedIn: Boolean = false,
    val userName: String = ""
)