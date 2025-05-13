package com.demo.delivery.refactoring.components.userdata

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.demo.delivery.refactoring.ui.theme.DeliveryTheme
import com.demo.delivery.refactoring.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun UserDataInputField(
    label: String,
    value: String,
    isEnabled: Boolean,
    onValueChange: (String) -> Unit,
) {

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        enabled = isEnabled,
        label = { Text(text = label) },
        onValueChange = onValueChange
    )


}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun PreviewUserDataInputField() = DeliveryTheme {
    UserDataInputField(
        label = "Email",
        value = "example@example.com",
        isEnabled = true,
        onValueChange = {}
    )
}