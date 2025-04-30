package com.demo.delivery.feature.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.delivery.R
import com.demo.delivery.core.navigation.AppScreens
import com.demo.delivery.core.navigation.localNavHost
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.core.theme.PREVIEW_DEVICE
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.core.theme.localDeliveryColors
import com.demo.delivery.feature.profile.view.shared.ArrowNavigateIcon

@Composable
fun ProfileAuthItem(
    isAuthorized: Boolean,
    userName: String,
) {

    val navController = localNavHost.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = localDeliveryColors.current.surfaceVariantLow)
            .clickable { navController.navigate(AppScreens.Login.route) }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(90.dp))
                .background(localDeliveryColors.current.surfaceVariantHigh)
                .size(44.dp)
        ) {
            if (isAuthorized) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = userName.first().toString(),
                    fontSize = 24.sp,
                    color = Color.White
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Outlined.Person,
                    contentDescription = stringResource(R.string.profile_user_content_description),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (isAuthorized) {

                Text(
                    text = userName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
                Text(
                    text = stringResource(R.string.profile_change_data),
                    color = localDeliveryColors.current.surfaceVariantHigh,
                    fontSize = 14.sp
                )
            } else {
                Text(
                    text = stringResource(R.string.profile_authorize),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))
        ArrowNavigateIcon()
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun ProfileAuthItemPreviewNotAuthorized() = DeliveryTheme {
    ProfileAuthItem(
        isAuthorized = false,
        userName = ""
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun ProfileAuthItemPreviewAuthorized() = DeliveryTheme {
    ProfileAuthItem(
        isAuthorized = true,
        userName = "Имя"
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun ProfileAuthItemPreviewAuthorizedAndSetName() = DeliveryTheme {
    ProfileAuthItem(
        isAuthorized = true,
        userName = "Андрей"
    )
}