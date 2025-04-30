package com.demo.delivery.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state by viewModel.state.observeAsState(LoginState())

    LoginView(
        state = state,
        onAction = viewModel::dispatchAction
    )
}