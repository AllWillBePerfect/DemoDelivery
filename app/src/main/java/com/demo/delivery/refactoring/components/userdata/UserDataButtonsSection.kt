package com.demo.delivery.refactoring.components.userdata

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.delivery.R
import com.demo.delivery.refactoring.ui.theme.DeliveryTheme
import com.demo.delivery.refactoring.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.refactoring.viewmodels.UserDataAction

@Composable
fun UserDataButtonsSection(
    isChangeMode: Boolean,
    buttonEnabled: Boolean,
    onAction: (UserDataAction) -> Unit
) {

    if (isChangeMode) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonEnabled,
            onClick = { onAction(UserDataAction.SaveChanges) },
        ) {
            Text(
                text = stringResource(R.string.user_data_button_save_changes)
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = {onAction(UserDataAction.LaunchDeleteDialog)}
            ) {
                Text(
                    text = stringResource(R.string.user_data_button_delete),
                    color = MaterialTheme.colorScheme.error

                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            TextButton(
                modifier = Modifier.weight(1f),

                onClick = {onAction(UserDataAction.LaunchExitDialog)}
            ) {
                Text(
                    text = stringResource(R.string.user_data_button_exit),
                )
            }
        }
    }


}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataButtonsSectionPreviewChangeModeEnabledAndButtonEnabled() = DeliveryTheme {
    UserDataButtonsSection(
        isChangeMode = true,
        buttonEnabled = true,
    ) {}
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataButtonsSectionPreviewChangeModeEnabledAndButtonDisable() = DeliveryTheme {
    UserDataButtonsSection(
        isChangeMode = true,
        buttonEnabled = false,
    ) {}
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataButtonsSectionPreviewChangeModeDisabled() = DeliveryTheme {
    UserDataButtonsSection(
        isChangeMode = false,
        buttonEnabled = true,
    ) {}
}