package com.demo.delivery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demo.delivery.core.navigation.AppScreens
import com.demo.delivery.core.navigation.localNavHost
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.feature.profile.ProfileScreen

@Composable
fun DeliveryApp(
    navController: NavHostController = rememberNavController(),
) = DeliveryTheme {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: AppScreens.Profile.route

    CompositionLocalProvider(
        localNavHost provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = currentScreen
        ) {
            composable(
                route = AppScreens.Profile.route
            ) { ProfileScreen() }

            composable(
                route = AppScreens
            ) {  }
        }
    }

}