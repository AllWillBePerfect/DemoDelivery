package com.demo.delivery.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Класс для хранения цветов приложения.
 */
data class DeliveryColors(
    val surfaceVariantHigh: Color,
    val surfaceVariantLow: Color,

    )

/**
 * [staticCompositionLocalOf] нужен, чтобы можно было получить доступ к цветам из любой compose функции, не передавая ее в параметры
 */
val localDeliveryColors = staticCompositionLocalOf<DeliveryColors> { error("No Colors Provided") }