package com.demo.delivery.ui.screens.userdata.models

sealed interface UserDataAction {

    // обновление текстовых полей
    data class UpdateUserTextPhone(val phone: String) : UserDataAction
    data class UpdateUserTextEmail(val email: String) : UserDataAction
    data class UpdateUserTextName(val name: String) : UserDataAction
    data class UpdateUserTextBirthday(val birthday: String) : UserDataAction
    data class UpdateUserTextAddress(val address: String) : UserDataAction
    data class UpdateUserTextApartment(val apartment: String) : UserDataAction
    data class UpdateUserTextEntrance(val entrance: String) : UserDataAction
    data class UpdateUserTextFloor(val floor: String) : UserDataAction
    data class UpdateUserTextIntercom(val intercom: String) : UserDataAction

    // меняет режим редактирования
    data object SwitchToChangeMode : UserDataAction

    // запуск диалоговых окон и действия с ними
    data object LaunchDeleteDialog : UserDataAction
    data object LaunchExitDialog : UserDataAction
    data object CloseDialog : UserDataAction
    data object ConfirmDeleteDialog : UserDataAction
    data object ConfirmExitDialog : UserDataAction

    //запуск view для выбора даты
    data object LaunchDataPicker : UserDataAction
    data object CloseDataPicker : UserDataAction
    data class ChooseDate(val date: Long) : UserDataAction

    // сохранение изменений
    data object SaveChanges : UserDataAction
}