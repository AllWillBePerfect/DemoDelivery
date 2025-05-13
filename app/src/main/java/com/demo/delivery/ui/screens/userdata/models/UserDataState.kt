package com.demo.delivery.ui.screens.userdata.models

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