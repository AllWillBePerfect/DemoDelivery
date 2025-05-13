package com.demo.delivery.components.userdata

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.delivery.ui.theme.DeliveryTheme
import com.demo.delivery.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun UserDataInputFieldShort(
    label: String,
    value: String,
    isEnabled: Boolean,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {


    Column(
        modifier = modifier
    ) {

        Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = value,
            singleLine = true,
            enabled = isEnabled,

            onValueChange = {text ->
                // Ограничение в 20 символов
                if (text.length <= 20) {
                    onValueChange(text)
                }
            }
        )
    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataInputFieldShortPreview() = DeliveryTheme {
    UserDataInputFieldShort(
        label = "Email",
        value = "",
        isEnabled = true,
        modifier = Modifier,
        onValueChange = {}
    )
}