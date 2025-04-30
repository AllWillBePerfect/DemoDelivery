package com.demo.delivery.feature.login

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
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.core.theme.PREVIEW_DEVICE
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.feature.login.view.LoginButtonSection
import com.demo.delivery.feature.login.view.LoginHeader
import com.demo.delivery.feature.login.view.LoginInputSection
import com.demo.delivery.feature.login.view.LoginPrivacySection

@Composable
fun LoginView(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(220.dp))

                LoginHeader(state.byEmailEnter)

                Spacer(Modifier.height(54.dp))

                LoginInputSection(
                    byEmailEnter = state.byEmailEnter,
                    userTextEmail = state.userTextEmail,
                    userTextPhone = state.userTextPhone,
                    onAction = onAction
                )
            }


            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                LoginButtonSection(
                    byEmailEnter = state.byEmailEnter,
                    emailButtonEnabled = state.emailButtonEnabled,
                    phoneButtonEnabled = state.phoneButtonEnabled
                )

                Spacer(Modifier.height(24.dp))

                LoginPrivacySection()
            }
        }
    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun LoginViewPreview() = DeliveryTheme {
    LoginView(LoginState()) {}
}