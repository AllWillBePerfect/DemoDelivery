package com.demo.delivery.data

/**
 * Класс данных, представляющий состояние экрана редактирования данных пользователя.
 *
 * @property isChangeMode флаг, указывающий, находится ли экран в режиме редактирования.
 * @property phoneTextInputEnabled флаг, указывающий, активировано ли текстовое поле для ввода номера телефона.
 * @property emailTextInputEnabled флаг, указывающий, активировано ли текстовое поле для ввода email.
 * @property userTextPhone текст, введенный пользователем в поле для номера телефона.
 * @property userTextPhoneError текст ошибки, связанной с вводом номера телефона, если есть.
 * @property userTextEmail текст, введенный пользователем в поле для email.
 * @property userTextEmailError текст ошибки, связанной с вводом email, если есть.
 * @property userTextName текст, введенный пользователем в поле для имени.
 * @property userTextBirthday текст, введенный пользователем в поле для даты рождения.
 * @property userTextBirthdayError текст ошибки, связанной с вводом даты рождения, если есть.
 * @property userTextAddress текст, введенный пользователем в поле для адреса.
 * @property userTextApartment текст, введенный пользователем в поле для квартиры.
 * @property userTextEntrance текст, введенный пользователем в поле для подъезда.
 * @property userTextFloor текст, введенный пользователем в поле для этажа.
 * @property userTextIntercom текст, введенный пользователем в поле для домофона.
 * @property isSaveChanges флаг, указывающий, есть ли изменения, которые нужно сохранить.
 * @property isDeleteDialogVisible флаг, указывающий, виден ли диалог удаления.
 * @property isExitDialogVisible флаг, указывающий, виден ли диалог выхода.
 * @property isDatePickerVisible флаг, указывающий, виден ли выбор даты.
 */
data class UserDataState(
    val isChangeMode: Boolean = false,
    val phoneTextInputEnabled: Boolean = false,
    val emailTextInputEnabled: Boolean = false,
    val userTextPhone: String = "",
    val userTextPhoneError: String? = null,
    val userTextEmail: String = "",
    val userTextEmailError: String? = null,
    val userTextName: String = "",
    val userTextBirthday: String = "",
    val userTextBirthdayError: String? = null,
    val userTextAddress: String = "",
    val userTextApartment: String = "",
    val userTextEntrance: String = "",
    val userTextFloor: String = "",
    val userTextIntercom: String = "",
    val isSaveChanges: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isExitDialogVisible: Boolean = false,
    val isDatePickerVisible: Boolean = false,
)