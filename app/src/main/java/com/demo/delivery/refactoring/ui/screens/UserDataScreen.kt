package com.demo.delivery.refactoring.ui.screens

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.delivery.R
import com.demo.delivery.refactoring.ui.navigation.AppScreens
import com.demo.delivery.refactoring.ui.navigation.CodeConfirmMethod
import com.demo.delivery.refactoring.ui.navigation.localNavHost
import com.demo.delivery.feature.userdata.DatePickerModal
import com.demo.delivery.feature.userdata.parseLongToDate
import com.demo.delivery.refactoring.components.userdata.UserDataAlertDialog
import com.demo.delivery.refactoring.components.userdata.UserDataButtonsSection
import com.demo.delivery.refactoring.components.userdata.UserDataInputField
import com.demo.delivery.refactoring.components.userdata.UserDataInputFieldDatePicker
import com.demo.delivery.refactoring.components.userdata.UserDataInputFieldEmail
import com.demo.delivery.refactoring.components.userdata.UserDataInputFieldPhone
import com.demo.delivery.refactoring.components.userdata.UserDataInputFieldShort
import com.demo.delivery.refactoring.viewmodels.UserDataAction
import com.demo.delivery.refactoring.viewmodels.UserDataState
import com.demo.delivery.refactoring.viewmodels.UserDataViewModel

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

                UserDataInputFieldPhone(
                    label = stringResource(R.string.user_data_label_phone),
                    value = state.userTextPhone,
                    isEnabled = state.phoneTextInputEnabled,
                    errorText = state.userTextPhoneError,
                    onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextPhone(it)) }
                )

                Spacer(Modifier.height(16.dp))

                UserDataInputFieldEmail(
                    label = stringResource(R.string.user_data_label_email),
                    value = state.userTextEmail,
                    isEnabled = state.emailTextInputEnabled,
                    errorText = state.userTextEmailError,
                    onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextEmail(it)) }

                )

                Spacer(Modifier.height(16.dp))

                UserDataInputField(
                    label = stringResource(R.string.user_data_label_name),
                    value = state.userTextName,
                    isEnabled = isChangeMode,
                    onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextName(it)) }
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
                    onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextBirthday(it)) },
                    launchDatePicker = { viewModel.dispatchAction(UserDataAction.LaunchDataPicker) }
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
                    onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextAddress(it)) }
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
                        onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextApartment(it)) }

                    )

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_entrance),
                        value = state.userTextEntrance,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextEntrance(it)) }

                    )

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_floor),
                        value = state.userTextFloor,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextFloor(it)) }

                    )

                    UserDataInputFieldShort(
                        label = stringResource(R.string.user_data_label_Intercom),
                        value = state.userTextIntercom,
                        modifier = Modifier.weight(1f),
                        isEnabled = isChangeMode,
                        onValueChange = { viewModel.dispatchAction(UserDataAction.UpdateUserTextIntercom(it)) }

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
                    onAction = viewModel::dispatchAction
                )
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
            DatePickerModal({ date ->
                date?.let {
                    viewModel.dispatchAction(UserDataAction.ChooseDate(it))
                    Log.d("UserDataView", "it: ${parseLongToDate(it)}")
                }
            }, {
                viewModel.dispatchAction(UserDataAction.CloseDataPicker)
            })
        }

    }


    LaunchedEffect(closeScreenEffect) {
        closeScreenEffect?.singleValue()?.let {
            navController.navigateUp()
        }
    }

    LaunchedEffect(navigateToCodeConfirmEffect) {
        navigateToCodeConfirmEffect?.singleValue()?.let {
            val argRoute = when (it) {
                is CodeConfirmMethod.Email -> "/email" + "/${it.email}"
                is CodeConfirmMethod.Phone -> "/phone" + "/${it.phoneNumber}"
            }
            navController.navigate(AppScreens.CodeConfirm.route + argRoute)
        }
    }


}