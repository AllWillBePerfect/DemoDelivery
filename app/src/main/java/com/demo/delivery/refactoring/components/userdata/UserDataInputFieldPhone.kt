package com.demo.delivery.refactoring.components.userdata

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.demo.delivery.refactoring.ui.theme.DeliveryTheme
import com.demo.delivery.refactoring.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.refactoring.utils.NanpVisualTransformation

@Composable
fun UserDataInputFieldPhone(
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
        visualTransformation = NanpVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        label = { Text(text = label) },
        onValueChange = {text ->
            if (text.length <= 10) {
                onValueChange(text)

            }
        }
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataInputFieldPhonePreview() = DeliveryTheme {
    UserDataInputFieldPhone(
        label = "Phone",
        value = "9992222222",
        isEnabled = true,
        errorText = null,
        onValueChange = {}
    )
}
