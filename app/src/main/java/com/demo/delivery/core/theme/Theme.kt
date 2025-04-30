package com.demo.delivery.core.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.demo.delivery.core.navigation.localNavHost

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF58A9FF)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF007AFF)
)


private val DeliveryDarkColor = DeliveryColors(
    surfaceVariantLow = Color(0x1FFFFFFF),
    surfaceVariantHigh = Color(0x99FFFFFF)
)

private val DeliveryLightColor = DeliveryColors(
    surfaceVariantLow = Color(0x0D3C3C43),
    surfaceVariantHigh = Color(0x993C3C43)
)

@Composable
fun DeliveryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {


    //По умолчанию используем стандартные цвета Material3
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    /**
     * Если будет нужен иной цвет, то воспользуемся [localDeliveryColors]
     */
    val deliveryColors = if (darkTheme) DeliveryDarkColor else DeliveryLightColor

    /**
     * [CompositionLocalProvider] нужен, чтобы можно было получить доступ к переменным, которые объявлены через [staticCompositionLocalOf]
     */
    CompositionLocalProvider(
        localDeliveryColors provides deliveryColors,
        localNavHost provides navController
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = {
                //Box с background color позволит корректно отображать цвет на preview элементов
                Box(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                ) {
                    content()
                }
            }
        )
    }


}