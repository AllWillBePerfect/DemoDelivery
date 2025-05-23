package com.demo.delivery.ui.screens.codeconfirm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.delivery.R
import com.demo.delivery.components.codeconfirm.CodeConfirmBottomSection
import com.demo.delivery.components.codeconfirm.CodeConfirmButtonSection
import com.demo.delivery.components.codeconfirm.CodeConfirmHeader
import com.demo.delivery.components.codeconfirm.CodeConfirmTimerSection
import com.demo.delivery.components.codeconfirm.CodeConfirmOtpSection
import com.demo.delivery.ui.navigation.AppScreens
import com.demo.delivery.ui.navigation.localNavHost
import com.demo.delivery.data.CodeConfirmState
import com.demo.delivery.viewmodels.CodeConfirmViewModel

@Composable
fun CodeConfirmPhoneScreen(
    viewModel: CodeConfirmViewModel = hiltViewModel(),
    phone: String
) {
    val state by viewModel.state.observeAsState(CodeConfirmState())
    val navigateToProfileEffect by viewModel.navigateToProfileEffect.observeAsState()

    val navController = localNavHost.current

    val byEmailEnter by remember { mutableStateOf(false) }

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
                    params = phone
                )

                Spacer(Modifier.height(32.dp))

                CodeConfirmOtpSection(
                    otpValue = state.otpValue,
                    isError = state.isError,
                    onAction = viewModel::dispatchAction
                )

                Spacer(Modifier.height(24.dp))

                CodeConfirmTimerSection(
                    timerValue = state.timerValue,
                    isError = state.isError,
                    byEmailEnter = byEmailEnter,
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
                    params = phone,
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