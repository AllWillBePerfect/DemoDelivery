package com.demo.delivery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.demo.delivery.ui.navigation.AppScreens
import com.demo.delivery.ui.navigation.localNavHost
import com.demo.delivery.ui.screens.codeconfirm.CodeConfirmEmailScreen
import com.demo.delivery.ui.screens.codeconfirm.CodeConfirmPhoneScreen
import com.demo.delivery.ui.screens.login.LoginScreen
import com.demo.delivery.ui.screens.profile.ProfileScreen
import com.demo.delivery.ui.screens.userdata.UserDataScreen
import com.demo.delivery.ui.theme.DeliveryTheme
import com.demo.delivery.viewmodels.MainViewModel

/**
 * Функция применяет `DeliveryTheme` к приложению и вызывает
 * `NavigationCompose` для задания навигации.
 */
@Composable
fun DeliveryApp(
) = DeliveryTheme {
    NavigationCompose()
}


/**
 * Навигация на основе компонента `NavHost`.
 * @param mainViewModel экземпляр [MainViewModel] для получения состояния авторизации
 * и обработки навигации с аргументами
 *
 */
@Composable
fun NavigationCompose(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = localNavHost.current
    val isLoggedIn by mainViewModel.isLoggedIn.observeAsState()


    isLoggedIn?.let {
        val startDestination =
            if (isLoggedIn == true) AppScreens.Profile.route else AppScreens.Login.route
        NavHost(
            navController = navController,
            startDestination = startDestination
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
                route = AppScreens.CodeConfirmEmail.route,
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                CodeConfirmEmailScreen(email = email)
            }

            composable(
                route = AppScreens.CodeConfirmPhone.route,
                arguments = listOf(
                    navArgument("phone") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val phone = backStackEntry.arguments?.getString("phone") ?: ""
                CodeConfirmPhoneScreen(phone = phone)
            }

        }
    }


}
