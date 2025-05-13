package com.demo.delivery.refactoring.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.delivery.refactoring.ui.navigation.AppScreens
import com.demo.delivery.refactoring.ui.navigation.CodeConfirmMethod
import com.demo.delivery.refactoring.ui.navigation.localNavHost
import com.demo.delivery.refactoring.components.codeconfirm.CodeConfirmBottomSection
import com.demo.delivery.refactoring.components.codeconfirm.CodeConfirmButtonSection
import com.demo.delivery.refactoring.components.codeconfirm.CodeConfirmHeader
import com.demo.delivery.refactoring.components.codeconfirm.CodeConfirmOtpSectionV3
import com.demo.delivery.refactoring.viewmodels.CodeConfirmState
import com.demo.delivery.refactoring.viewmodels.CodeConfirmViewModel

@Composable
fun CodeConfirmScreen(
    viewModel: CodeConfirmViewModel = hiltViewModel(),
    params: CodeConfirmMethod
) {

    val state by viewModel.state.observeAsState(CodeConfirmState())
    val navigateToProfileEffect by viewModel.navigateToProfileEffect.observeAsState()

    val navController = localNavHost.current

    val byEmailEnter = params is CodeConfirmMethod.Email

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(Modifier.height(220.dp))

                CodeConfirmHeader(
                    byEmailEnter = byEmailEnter,
                    params = params
                )

                Spacer(Modifier.height(32.dp))

                CodeConfirmOtpSectionV3(
                    timerValue = state.timerValue,
                    isError = state.isError,
                    buttonEnabled = state.buttonEnabled,
                    onAction = viewModel::dispatchAction
                )


            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {

                CodeConfirmButtonSection(
                    buttonEnabled = state.buttonEnabled,
                    byEmailEnter = byEmailEnter,
                    params = params,
                    onAction = viewModel::dispatchAction
                )

                Spacer(Modifier.height(24.dp))

                CodeConfirmBottomSection(
                    byEmailEnter = byEmailEnter
                )
            }
        }
    }

    LaunchedEffect(navigateToProfileEffect) {
        navigateToProfileEffect?.singleValue()?.let {
            navController.navigate(AppScreens.Profile.route) {
                popUpTo(AppScreens.Profile.route) {inclusive = true}
            }
        }
    }

}