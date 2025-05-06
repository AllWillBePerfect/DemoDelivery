package com.demo.delivery.feature.codeconfirm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.delivery.core.navigation.AppScreens
import com.demo.delivery.core.navigation.CodeConfirmMethod
import com.demo.delivery.core.navigation.localNavHost

@Composable
fun CodeConfirmScreen(
    viewModel: CodeConfirmViewModel = hiltViewModel(),
    params: CodeConfirmMethod
) {

    val state by viewModel.state.observeAsState(CodeConfirmState())
    val navigateToProfileEffect by viewModel.navigateToProfileEffect.observeAsState()

    val navController = localNavHost.current

    CodeConfirmView(
        state = state,
        onAction = viewModel::dispatchAction,
        params = params
    )

    LaunchedEffect(navigateToProfileEffect) {
        navigateToProfileEffect?.singleValue()?.let {
            navController.navigate(AppScreens.Profile.route) {
                popUpTo(AppScreens.Profile.route) {inclusive = true}
            }
        }
    }

}