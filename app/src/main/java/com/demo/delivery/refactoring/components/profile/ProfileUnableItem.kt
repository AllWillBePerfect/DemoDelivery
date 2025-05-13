package com.demo.delivery.refactoring.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.demo.delivery.refactoring.ui.theme.DeliveryTheme
import com.demo.delivery.refactoring.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun ProfileUnableItem(
    title: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)

            // Пункт 13.	На экране "Профиль" кнопки "История заказов",
            // "Вопросы и ответы" и т.д. кликабельны, но никуда не ведут.
            // То есть нажатие на них должно подсвечиваться;

            // .clickable добавляет реакцию на нажатие.
            .clickable {}
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