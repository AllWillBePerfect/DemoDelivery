package com.demo.delivery.feature.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.delivery.R
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.core.theme.PREVIEW_DEVICE
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.core.theme.localDeliveryColors
import com.demo.delivery.feature.profile.view.shared.ArrowNavigateIcon

@Composable
fun ProfileUnableItem(
    title: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = localDeliveryColors.current.surfaceVariantLow)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically


    ) {

        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = title,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(16.dp))
        ArrowNavigateIcon()
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(
    uiMode = PREVIEW_UI_MODE_DARK,
    device = PREVIEW_DEVICE
)
@Preview(
    uiMode = PREVIEW_UI_MODE_LIGHT,
    device = PREVIEW_DEVICE
)
@Composable
fun ProfileUnableItemPreview() = DeliveryTheme {
        ProfileUnableItem(stringResource(R.string.profile_order_history))
}