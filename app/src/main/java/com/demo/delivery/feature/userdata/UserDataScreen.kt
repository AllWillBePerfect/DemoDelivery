package com.demo.delivery.feature.userdata

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.delivery.core.navigation.AppScreens
import com.demo.delivery.core.navigation.CodeConfirmMethod
import com.demo.delivery.core.navigation.localNavHost

@Composable
fun UserDataScreen(
    viewModel: UserDataViewModel = hiltViewModel()
) {

    val navController = localNavHost.current

    val state by viewModel.state.observeAsState(UserDataState())
    val closeScreenEffect by viewModel.closeScreenEffect.observeAsState()
    val navigateToCodeConfirmEffect by viewModel.navigateToCodeConfirmEffect.observeAsState()

    UserDataView(
        state = state,
        onAction = viewModel::dispatchAction
    )


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