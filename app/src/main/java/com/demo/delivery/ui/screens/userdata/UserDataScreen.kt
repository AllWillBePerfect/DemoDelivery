package com.demo.delivery.ui.screens.userdata

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.delivery.R
import com.demo.delivery.components.userdata.UserDataAlertDialog
import com.demo.delivery.components.userdata.UserDataInputFieldShort
import com.demo.delivery.data.UserDataAction
import com.demo.delivery.data.UserDataState
import com.demo.delivery.ui.navigation.AppScreens
import com.demo.delivery.ui.navigation.CodeConfirmMethod
import com.demo.delivery.ui.navigation.localNavHost
import com.demo.delivery.utils.DateVisualTransformation
import com.demo.delivery.utils.NanpVisualTransformation
import com.demo.delivery.viewmodels.UserDataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataScreen(
    viewModel: UserDataViewModel = hiltViewModel()
) {


    val state by viewModel.state.observeAsState(UserDataState())
    val closeScreenEffect by viewModel.closeScreenEffect.observeAsState()
    val navigateToCodeConfirmEffect by viewModel.navigateToCodeConfirmEffect.observeAsState()

    val navController = localNavHost.current

    val isChangeMode = state.isChangeMode

    val datePickerState = rememberDatePickerState()


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
                        IconButton(onClick = { viewModel.dispatchAction(UserDataAction.SwitchToChangeMode) }) {
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

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.userTextPhone,
                    enabled = state.phoneTextInputEnabled,
                    isError = state.userTextPhoneError != null,
                    visualTransformation = NanpVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text(text = stringResource(R.string.user_data_label_phone)) },
                    onValueChange = { text ->
                        if (text.length <= 10) {
                            viewModel.dispatchAction(UserDataAction.UpdateUserTextPhone(text))

                        }
                    }
                )

                state.userTextPhoneError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }


                Spacer(Modifier.height(16.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.userTextEmail,
                    enabled = state.emailTextInputEnabled,
                    isError = state.userTextEmailError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text(text = stringResource(R.string.user_data_label_email)) },
                    onValueChange = { text ->
                        viewModel.dispatchAction(UserDataAction.UpdateUserTextEmail(text))
                    }
                )
                state.userTextEmailError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.userTextName,
                    enabled = isChangeMode,
                    label = { Text(text = stringResource(R.string.user_data_label_name)) },
                    onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextName(it)) }
                )

                Spacer(Modifier.height(16.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.userTextBirthday,
                    enabled = isChangeMode,
                    label = { Text(text = stringResource(R.string.user_data_label_birthday)) },
                    visualTransformation = DateVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    isError = state.userTextBirthdayError != null,
                    trailingIcon = {
                        IconButton(
                            enabled = isChangeMode,
                            onClick = {
                                viewModel.dispatchAction(UserDataAction.LaunchDataPicker)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = stringResource(R.string.user_date_icon_calendar)
                            )
                        }
                    },
                    onValueChange = { text ->
                        if (text.length <= 8)
                            viewModel.dispatchAction(UserDataAction.UpdateUserTextBirthday(text))
                    }
                )
                state.userTextBirthdayError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))



                Text(
                    text = stringResource(R.string.user_data_label_address),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W700
                )

                Spacer(Modifier.height(16.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.userTextAddress,
                    enabled = isChangeMode,
                    label = { Text(text = stringResource(R.string.user_data_label_address)) },
                    onValueChange = {
                        viewModel.dispatchAction(
                            UserDataAction.UpdateUserTextAddress(
                                it
                            )
                        )
                    }
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
                        onValueChange = {
                            viewModel.dispatchAction(
                                UserDataAction.UpdateUserTextApartment(
                                    it
                                )
                            )
                        }

                    )

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_entrance),
                        value = state.userTextEntrance,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = {
                            viewModel.dispatchAction(
                                UserDataAction.UpdateUserTextEntrance(
                                    it
                                )
                            )
                        }

                    )

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_floor),
                        value = state.userTextFloor,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = {
                            viewModel.dispatchAction(
                                UserDataAction.UpdateUserTextFloor(
                                    it
                                )
                            )
                        }

                    )

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_Intercom),
                        value = state.userTextIntercom,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = {
                            viewModel.dispatchAction(
                                UserDataAction.UpdateUserTextIntercom(
                                    it
                                )
                            )
                        }

                    )

                }


            }


            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
            ) {

                if (isChangeMode) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = state.isSaveChanges,
                        onClick = { viewModel.dispatchAction(UserDataAction.SaveChanges) },
                    ) {
                        Text(
                            text = stringResource(R.string.user_data_button_save_changes)
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.dispatchAction(UserDataAction.LaunchDeleteDialog) }
                        ) {
                            Text(
                                text = stringResource(R.string.user_data_button_delete),
                                color = MaterialTheme.colorScheme.error

                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        TextButton(
                            modifier = Modifier.weight(1f),

                            onClick = { viewModel.dispatchAction(UserDataAction.LaunchExitDialog) }
                        ) {
                            Text(
                                text = stringResource(R.string.user_data_button_exit),
                            )
                        }
                    }
                }
            }

        }


        if (state.isDeleteDialogVisible) {
            UserDataAlertDialog(
                isDelete = true,
                onDismiss = { viewModel.dispatchAction(UserDataAction.CloseDialog) },
                onConfirm = { viewModel.dispatchAction(UserDataAction.ConfirmDeleteDialog) }
            )
        }

        if (state.isExitDialogVisible) {
            UserDataAlertDialog(
                isDelete = false,
                onDismiss = { viewModel.dispatchAction(UserDataAction.CloseDialog) },
                onConfirm = { viewModel.dispatchAction(UserDataAction.ConfirmExitDialog) }
            )
        }

        if (state.isDatePickerVisible) {


            DatePickerDialog(
                onDismissRequest = { viewModel.dispatchAction(UserDataAction.CloseDataPicker) },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            viewModel.dispatchAction(UserDataAction.ChooseDate(it))
                            viewModel.dispatchAction(UserDataAction.CloseDataPicker)
                        }
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.dispatchAction(UserDataAction.CloseDataPicker) }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

    }


    LaunchedEffect(closeScreenEffect) {
        closeScreenEffect?.singleValue()?.let {
            navController.navigateUp()
        }
    }

    LaunchedEffect(navigateToCodeConfirmEffect) {
        navigateToCodeConfirmEffect?.singleValue()?.let {
            when (it) {
                is CodeConfirmMethod.Email -> navController.navigate(
                    AppScreens.CodeConfirmEmail.createRoute(
                        it.email
                    )
                )

                is CodeConfirmMethod.Phone -> navController.navigate(
                    AppScreens.CodeConfirmPhone.createRoute(
                        it.phoneNumber
                    )
                )
            }
        }
    }


}