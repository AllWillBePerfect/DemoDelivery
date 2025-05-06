package com.demo.delivery.feature.userdata.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.core.theme.PREVIEW_DEVICE
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun UserDataInputFieldEmail(
    label: String,
    value: String,
    isEnabled: Boolean,
    errorText: String?,
    onValueChange: (String) -> Unit,
) {

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        enabled = isEnabled,
        isError = errorText != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        label = { Text(text = label) },
        onValueChange = {text ->
                onValueChange(text)
        }
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataInputFieldEmailPreview() = DeliveryTheme {
    UserDataInputFieldEmail(
        label = "Email",
        value = "example@example.com",
        isEnabled = true,
        errorText = null,
        onValueChange = {}
    )
}