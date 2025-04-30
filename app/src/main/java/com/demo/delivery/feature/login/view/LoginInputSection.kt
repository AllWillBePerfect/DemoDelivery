package com.demo.delivery.feature.login.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.demo.delivery.R
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.core.theme.PREVIEW_DEVICE
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.feature.login.LoginAction
import com.demo.delivery.feature.login.formatter.NanpVisualTransformation


@Composable
fun LoginInputSection(
    byEmailEnter: Boolean,
    userTextEmail: String,
    userTextPhone: String,
    onAction: (LoginAction) -> Unit
) {

    val textButtonValue =
        if (byEmailEnter)
            stringResource(R.string.login_enter_by_phone_button)
        else
            stringResource(R.string.login_enter_by_email)

    val numericRegex = Regex("[^0-9]")

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (byEmailEnter) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = userTextEmail,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                placeholder = { Text(text = stringResource(R.string.login_email_example)) },
                singleLine = true,
                onValueChange = { onAction(LoginAction.UpdateUserTextEmail(it)) }
            )
        } else {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = userTextPhone,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                placeholder = { Text(text = stringResource(R.string.login_phone_example)) },
                visualTransformation = NanpVisualTransformation(),
                singleLine = true,
                onValueChange = {
                    val stripped = numericRegex.replace(it, "")
                    val text = if (stripped.length >= 10) {
                        stripped.substring(0..9)
                    } else {
                        stripped
                    }
                    onAction(LoginAction.UpdateUserTextPhone(text))
                }
            )
        }

        TextButton(onClick = { onAction(LoginAction.SwitchAuthMethod) }) {
            Text(textButtonValue)
        }

    }
}



@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun LoginInputSectionPreviewEnterByPhone() = DeliveryTheme {
    LoginInputSection(
        byEmailEnter = false,
        userTextEmail = "",
        userTextPhone = "",
    ) {}
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun LoginInputSectionPreviewEnterByEmail() = DeliveryTheme {
    LoginInputSection(
        byEmailEnter = true,
        userTextEmail = "",
        userTextPhone = "",
    ) {}
}