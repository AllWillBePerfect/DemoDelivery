package com.demo.delivery.refactoring.components.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.delivery.R
import com.demo.delivery.refactoring.ui.theme.DeliveryTheme
import com.demo.delivery.refactoring.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun LoginHeader(
    byEmailEnter: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Outlined.Home,
            contentDescription = stringResource(R.string.login_enter_icon),
            modifier = Modifier.size(52.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(8.dp))

        Text(
            modifier = Modifier,
            text = if (byEmailEnter) stringResource(R.string.login_enter_by_email_lowercase) else stringResource(
                R.string.login_enter_by_phone
            ),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.W500,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(8.dp))

        Text(
            modifier = Modifier,
            text = stringResource(R.string.login_enter_to_get_bonuses),
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun LoginHeaderPreviewEnterByPhone() = DeliveryTheme {
    LoginHeader(
        byEmailEnter = false
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun LoginHeaderPreviewEnterByEmail() = DeliveryTheme {
    LoginHeader(
        byEmailEnter = true
    )
}