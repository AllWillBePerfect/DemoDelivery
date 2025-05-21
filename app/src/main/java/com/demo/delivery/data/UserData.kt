package com.demo.delivery.data

/**
 * Класс данных, представляющий информацию о пользователе.
 *
 * @property isLoggedIn флаг, указывающий, авторизован ли пользователь.
 * @property isEmailEnter флаг, указывающий, использовал ли пользователь email для входа.
 * @property phone номер телефона пользователя.
 * @property email адрес электронной почты пользователя.
 * @property name имя пользователя.
 * @property birthday дата рождения пользователя в формате строки.
 * @property address адрес пользователя.
 * @property apartment номер квартиры пользователя.
 * @property entrance номер подъезда пользователя.
 * @property floor этаж, на котором находится квартира пользователя.
 * @property intercom код домофона пользователя.
 */
data class UserData(
    val isLoggedIn: Boolean,
    val isEmailEnter: Boolean,
    val phone: String,
    val email: String,
    val name: String,
    val birthday: String,
    val address: String,
    val apartment: String,
    val entrance: String,
    val floor: String,
    val intercom: String,
)