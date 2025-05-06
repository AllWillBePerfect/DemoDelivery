package com.demo.delivery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.demo.delivery.core.navigation.AppScreens
import com.demo.delivery.core.navigation.CodeConfirmMethod
import com.demo.delivery.core.navigation.localNavHost
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.feature.codeconfirm.CodeConfirmScreen
import com.demo.delivery.feature.login.LoginScreen
import com.demo.delivery.feature.profile.ProfileScreen
import com.demo.delivery.feature.userdata.UserDataScreen

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
 * @param navigationViewModel экземпляр [NavigationViewModel] для получения состояния авторизации
 * и обработки навигации с аргументами
 *
 */
@Composable
fun NavigationCompose(
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    val navController = localNavHost.current
    val isLoggedIn by navigationViewModel.isLoggedIn.observeAsState()
    val navigationRoute by navigationViewModel.navigateEffect.observeAsState()

    LaunchedEffect(navigationRoute) {

        navigationRoute?.let { route ->

            route.singleValue()?.let {
                navController.navigate(it)
            }
        }
    }


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
                route = AppScreens.CodeConfirm.route + "/{method}" + "/{value}",
                arguments = listOf(
                    navArgument("method") { type = NavType.StringType },
                    navArgument("value") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val method = backStackEntry.arguments?.getString("method") ?: ""
                val value = backStackEntry.arguments?.getString("value") ?: ""
                val params = if (method == "email")
                    CodeConfirmMethod.Email(value)
                else
                    CodeConfirmMethod.Phone(value)


                CodeConfirmScreen(
                    params = params
                )
            }
        }
    }


}
