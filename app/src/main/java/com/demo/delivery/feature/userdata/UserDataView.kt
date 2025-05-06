package com.demo.delivery.feature.userdata

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.delivery.R
import com.demo.delivery.core.navigation.localNavHost
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.core.theme.PREVIEW_DEVICE
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.feature.userdata.view.UserDataAlertDialog
import com.demo.delivery.feature.userdata.view.UserDataButtonsSection
import com.demo.delivery.feature.userdata.view.UserDataInputField
import com.demo.delivery.feature.userdata.view.UserDataInputFieldDatePicker
import com.demo.delivery.feature.userdata.view.UserDataInputFieldEmail
import com.demo.delivery.feature.userdata.view.UserDataInputFieldPhone
import com.demo.delivery.feature.userdata.view.UserDataInputFieldShort
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataView(
    state: UserDataState,
    onAction: (UserDataAction) -> Unit
) {

    val navController = localNavHost.current

    val isChangeMode = state.isChangeMode

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.user_data_account_date))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.user_data_back_arrow)
                        )
                    }
                },
                actions = {
                    if (!isChangeMode) {
                        IconButton(onClick = { onAction(UserDataAction.SwitchToChangeMode) }) {
                            Icon(
                                imageVector = Icons.Outlined.Create,
                                contentDescription = stringResource(R.string.user_data_change)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Spacer(Modifier.height(16.dp))

                UserDataInputFieldPhone(
                    label = stringResource(R.string.user_data_label_phone),
                    value = state.userTextPhone,
                    isEnabled = state.phoneTextInputEnabled,
                    errorText = state.userTextPhoneError,
                    onValueChange = { onAction(UserDataAction.UpdateUserTextPhone(it)) }
                )

                Spacer(Modifier.height(16.dp))

                UserDataInputFieldEmail(
                    label = stringResource(R.string.user_data_label_email),
                    value = state.userTextEmail,
                    isEnabled = state.emailTextInputEnabled,
                    errorText = state.userTextEmailError,
                    onValueChange = { onAction(UserDataAction.UpdateUserTextEmail(it)) }

                )

                Spacer(Modifier.height(16.dp))

                UserDataInputField(
                    label = stringResource(R.string.user_data_label_name),
                    value = state.userTextName,
                    isEnabled = isChangeMode,
                    onValueChange = { onAction(UserDataAction.UpdateUserTextName(it)) }
                )

                Spacer(Modifier.height(16.dp))

                /* UserDataInputField(
                     label = stringResource(R.string.user_data_label_birthday),
                     value = state.userTextBirthday,
                     isEnabled = isChangeMode,
                     isDateField = true,
                     onValueChange = { onAction(UserDataAction.UpdateUserTextBirthday(it)) }
                 )*/

                UserDataInputFieldDatePicker(
                    label = stringResource(R.string.user_data_label_birthday),
                    value = state.userTextBirthday,
                    isEnabled = isChangeMode,
                    errorText = state.userTextBirthdayError,
                    onValueChange = { onAction(UserDataAction.UpdateUserTextBirthday(it)) },
                    launchDatePicker = { onAction(UserDataAction.LaunchDataPicker) }
                )

                Spacer(Modifier.height(16.dp))



                Text(
                    text = stringResource(R.string.user_data_label_address),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W700
                )

                Spacer(Modifier.height(16.dp))

                UserDataInputField(
                    label = stringResource(R.string.user_data_label_address),
                    value = state.userTextAddress,
                    isEnabled = isChangeMode,
                    onValueChange = { onAction(UserDataAction.UpdateUserTextAddress(it)) }
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_apartment),
                        value = state.userTextApartment,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = { onAction(UserDataAction.UpdateUserTextApartment(it)) }

                    )

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_entrance),
                        value = state.userTextEntrance,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = { onAction(UserDataAction.UpdateUserTextEntrance(it)) }

                    )

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_floor),
                        value = state.userTextFloor,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = { onAction(UserDataAction.UpdateUserTextFloor(it)) }

                    )

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_Intercom),
                        value = state.userTextIntercom,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = { onAction(UserDataAction.UpdateUserTextIntercom(it)) }

                    )

                }


            }


            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
            ) {
                UserDataButtonsSection(
                    isChangeMode = state.isChangeMode,
                    buttonEnabled = state.isSaveChanges,
                    onAction = onAction
                )
            }

        }


        if (state.isDeleteDialogVisible) {
            UserDataAlertDialog(
                isDelete = true,
                onDismiss = { onAction(UserDataAction.CloseDialog) },
                onConfirm = { onAction(UserDataAction.ConfirmDeleteDialog) }
            )
        }

        if (state.isExitDialogVisible) {
            UserDataAlertDialog(
                isDelete = false,
                onDismiss = { onAction(UserDataAction.CloseDialog) },
                onConfirm = { onAction(UserDataAction.ConfirmExitDialog) }
            )
        }

        if (state.isDatePickerVisible) {
            DatePickerModal({ date ->
                date?.let {
                    onAction(UserDataAction.ChooseDate(it))
                    Log.d("UserDataView", "it: ${parseLongToDate(it)}")
                }
            }, {
                onAction(UserDataAction.CloseDataPicker)
            })
        }

    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataViewPreview() = DeliveryTheme {
    UserDataView(
        state = UserDataState(),
        onAction = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


fun parseLongToDate(dateInMillis: Long?): String {
    return if (dateInMillis != null) {
        // Создаем объект Date из миллисекунд
        val date = Date(dateInMillis)

        // Форматируем дату в строку
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.format(date)
    } else {
        "Invalid date"
    }
}