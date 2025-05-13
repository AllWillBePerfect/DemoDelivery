package com.demo.delivery.refactoring.components.profile

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ArrowNavigateIcon(
) {
    Icon(
        modifier = Modifier
            .size(32.dp),
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = "Иконка навигации",
        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
}