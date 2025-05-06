package com.demo.delivery.feature.userdata.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.demo.delivery.R
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.core.theme.PREVIEW_DEVICE
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun UserDataAlertDialog(
    isDelete: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit
) {

    val title =
        stringResource(
            if (isDelete)
                R.string.user_data_dialog_title_delete
            else
                R.string.user_data_dialog_title_exit
        )

    val subtitle =
        stringResource(
            if (isDelete)
                R.string.user_data_dialog_subtitle_delete
            else
                R.string.user_data_dialog_subtitle_exit
        )

    val confirmButtonText =
        stringResource(
            if (isDelete)
                R.string.user_data_dialog_button_delete
            else
                R.string.user_data_dialog_button_exit
        )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Text(
                text = subtitle,
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmButtonText
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.user_data_dialog_button_cancel)
                )
            }
        },

        )

}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataAlertDialogPreviewIsDelete() = DeliveryTheme {
    UserDataAlertDialog(isDelete = true, onDismiss = {}, onConfirm = {})
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataAlertDialogPreviewIsExit() = DeliveryTheme {
    UserDataAlertDialog(isDelete = false, onDismiss = {}, onConfirm = {})
}

