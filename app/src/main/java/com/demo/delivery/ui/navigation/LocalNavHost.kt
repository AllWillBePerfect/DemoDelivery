package com.demo.delivery.ui.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

/**
 * [staticCompositionLocalOf] позволит нам не передавать NavHostController во всех компонентах через конструктор,
 * а просто вызвать в нужном.
 */
val localNavHost = staticCompositionLocalOf<NavHostController> { error("No default nav host") }