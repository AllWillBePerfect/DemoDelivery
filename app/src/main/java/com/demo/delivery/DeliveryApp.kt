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
import com.demo.delivery.feature.codeconfirm.CodeConfirmScreen
import com.demo.delivery.feature.login.LoginScreen
import com.demo.delivery.feature.profile.ProfileScreen
import com.demo.delivery.feature.userdata.UserDataScreen

@Composable
fun DeliveryApp(
    navController: NavHostController = rememberNavController(),
) = DeliveryTheme {

    val navController = localNavHost.current

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: AppScreens.Profile.route


    NavHost(
        navController = navController,
        startDestination = currentScreen
    ) {
        composable(
            route = AppScreens.Profile.route
        ) { ProfileScreen() }

        composable(
            route = AppScreens.UserData.route
        ) { UserDataScreen() }

        composable(
            route = AppScreens.Login.route
        ) { LoginScreen() }

        composable(
            route = AppScreens.CodeConfirm.route
        ) { CodeConfirmScreen() }
    }


}