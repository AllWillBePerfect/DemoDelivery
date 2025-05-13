package com.demo.delivery.refactoring.components.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.delivery.R
import com.demo.delivery.refactoring.ui.navigation.AppScreens
import com.demo.delivery.refactoring.ui.navigation.localNavHost
import com.demo.delivery.refactoring.ui.theme.DeliveryTheme
import com.demo.delivery.refactoring.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun LoginButtonSection(
    byEmailEnter: Boolean,
    emailButtonEnabled: Boolean,
    phoneButtonEnabled: Boolean,
    email: String,
    phone: String,
) {

    val navController = localNavHost.current

    val buttonText =
        if (byEmailEnter)
            stringResource(R.string.login_request_email_code)
        else
            stringResource(R.string.login_request_sms_code)

    val buttonEnabledState =
        byEmailEnter && emailButtonEnabled || !byEmailEnter && phoneButtonEnabled

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onClick = {
                // Пункт 5.	После ввода корректного телефона/почты и нажатия на "Отправить код"
                // происходит переход на экран ввода кода;
                if (byEmailEnter) {
                    //если вход по почте, то отправляем аргументы для почты
                    navController.navigate(AppScreens.CodeConfirm.route + "/email" + "/$email")
                } else {
                    //если вход по телефону, то отправляем аргументы для телефона
                    navController.navigate(AppScreens.CodeConfirm.route + "/phone" + "/$phone")

                }
            },
            enabled = buttonEnabledState
        ) {
            Text(
                text = buttonText,
                color = Color.White,
            )
        }

        TextButton(onClick = {
            navController.navigate(AppScreens.Profile.route) {
                popUpTo(AppScreens.Login.route) { inclusive = true }
            }
        }) {
            Text(text = stringResource(R.string.login_skip))
        }
    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun LoginButtonSectionPreviewEnterByPhoneAndEnabled() = DeliveryTheme {
    LoginButtonSection(
        byEmailEnter = false,
        phoneButtonEnabled = true,
        emailButtonEnabled = false,
        email = "",
        phone = ""
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun LoginButtonSectionPreviewEnterByEmailAndEnabled() = DeliveryTheme {
    LoginButtonSection(
        byEmailEnter = true,
        phoneButtonEnabled = false,
        emailButtonEnabled = true,
        email = "",
        phone = ""
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun LoginButtonSectionPreviewEnterByPhoneAndDisabled() = DeliveryTheme {
    LoginButtonSection(
        byEmailEnter = false,
        phoneButtonEnabled = false,
        emailButtonEnabled = false,
        email = "",
        phone = ""
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun LoginButtonSectionPreviewEnterByEmailAndDisabled() = DeliveryTheme {
    LoginButtonSection(
        byEmailEnter = true,
        phoneButtonEnabled = false,
        emailButtonEnabled = false,
        email = "",
        phone = ""
    )
}

