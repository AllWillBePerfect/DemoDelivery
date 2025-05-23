package com.demo.delivery.components.codeconfirm

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.demo.delivery.R
import com.demo.delivery.data.CodeConfirmAction
import com.demo.delivery.ui.theme.DeliveryTheme
import com.demo.delivery.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun CodeConfirmButtonSection(
    buttonEnabled: Boolean,
    byEmailEnter: Boolean,
    params: String,
    onAction: (CodeConfirmAction) -> Unit
) {

    val buttonText = stringResource(R.string.code_confirm_continue_button)

    Button(
        modifier = Modifier.fillMaxWidth(),
        enabled = buttonEnabled,
        onClick = {
            if (byEmailEnter)
                onAction(CodeConfirmAction.AuthByEmail(params))
            else
                onAction(CodeConfirmAction.AuthByPhone(params))
        }
    ) {
        Text(text = buttonText)
    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun PreviewCodeConfirmButtonSectionButtonEnabled() = DeliveryTheme {
    CodeConfirmButtonSection(
        buttonEnabled = true,
        byEmailEnter = true,
        params = "",
        onAction = {}
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun PreviewCodeConfirmButtonSectionButtonDisabled() = DeliveryTheme {
    CodeConfirmButtonSection(
        buttonEnabled = false,
        byEmailEnter = true,
        params = "",
        onAction = {}
    )
}