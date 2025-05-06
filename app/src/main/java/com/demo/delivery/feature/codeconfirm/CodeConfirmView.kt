package com.demo.delivery.feature.codeconfirm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.delivery.NavigationViewModel
import com.demo.delivery.core.navigation.CodeConfirmMethod
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.core.theme.PREVIEW_DEVICE
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.feature.codeconfirm.view.CodeConfirmBottomSection
import com.demo.delivery.feature.codeconfirm.view.CodeConfirmButtonSection
import com.demo.delivery.feature.codeconfirm.view.CodeConfirmHeader
import com.demo.delivery.feature.codeconfirm.view.CodeConfirmOtpSectionV3

@Composable
fun CodeConfirmView(
    state: CodeConfirmState,
    onAction: (CodeConfirmAction) -> Unit,
    params: CodeConfirmMethod
) {

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
                    onAction = onAction
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
                    onAction = onAction
                )

                Spacer(Modifier.height(24.dp))

                CodeConfirmBottomSection(
                    byEmailEnter = byEmailEnter
                )
            }
        }
    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun CodeConfirmViewPreview() = DeliveryTheme {
    CodeConfirmView(
        state = CodeConfirmState(),
        onAction = {},
        params = CodeConfirmMethod.Email(
            ""
        )
    )
}

