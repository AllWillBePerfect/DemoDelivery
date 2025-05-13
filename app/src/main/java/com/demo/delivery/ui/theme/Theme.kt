package com.demo.delivery.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.demo.delivery.ui.navigation.localNavHost

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

/**
 * Пункт 1.	Темная тема меняется в зависимости от настроек темы на устройстве;
 *
 * AndroidStudio при создании проекта на Compose сразу создает поддержку светлой и темной темы.
 *
 * @param darkTheme находится ли система в темной теме.
 * @param navController контроллер навигации. Он находится внутри `DeliveryTheme` чтобы при передаче в
 * `Preview` функции не было ошибок.
 * @param content дальнейшие `Compose` функции
 *
 */
@Composable
fun DeliveryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {


    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Если нужен цвет, которого нет в стандартной теме
    val deliveryColors = if (darkTheme) DeliveryDarkColor else DeliveryLightColor

    // С помощью CompositionLocalProvider объявим переменные, которые сможем получать их любой Compose функции
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