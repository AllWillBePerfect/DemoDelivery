package com.demo.delivery.refactoring.components.userdata

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.demo.delivery.R
import com.demo.delivery.refactoring.ui.theme.DeliveryTheme
import com.demo.delivery.refactoring.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.refactoring.utils.DateVisualTransformation

@Composable
fun UserDataInputFieldDatePicker(
    label: String,
    value: String,
    isEnabled: Boolean,
    errorText: String? = null,
    onValueChange: (String) -> Unit,
    launchDatePicker: () -> Unit
) {

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        enabled = isEnabled,
        label = { Text(text = label) },
        visualTransformation = DateVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        isError = errorText != null,
        trailingIcon = {
            IconButton(
                enabled = isEnabled,
                onClick = { launchDatePicker() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = stringResource(R.string.user_date_icon_calendar)
                )
            }
        },
        onValueChange = { text ->
            if (text.length <= 8)
                onValueChange(text)
        }
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun UserDataInputFieldDatePickerPreview() = DeliveryTheme {
    UserDataInputFieldDatePicker(
        label = "Email",
        value = "example@example.com",
        isEnabled = true,
        onValueChange = {},
        launchDatePicker = {}
    )
}