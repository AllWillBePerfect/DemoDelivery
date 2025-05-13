package com.demo.delivery.refactoring.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.delivery.refactoring.components.login.LoginButtonSection
import com.demo.delivery.refactoring.components.login.LoginHeader
import com.demo.delivery.refactoring.components.login.LoginInputSection
import com.demo.delivery.refactoring.components.login.LoginPrivacySection
import com.demo.delivery.refactoring.viewmodels.LoginState
import com.demo.delivery.refactoring.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val state by viewModel.state.observeAsState(LoginState())

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(220.dp))

                LoginHeader(state.byEmailEnter)

                Spacer(Modifier.height(54.dp))

                LoginInputSection(
                    byEmailEnter = state.byEmailEnter,
                    userTextEmail = state.userTextEmail,
                    userTextPhone = state.userTextPhone,
                    onAction = viewModel::dispatchAction
                )
            }


            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                LoginButtonSection(
                    byEmailEnter = state.byEmailEnter,
                    emailButtonEnabled = state.emailButtonEnabled,
                    phoneButtonEnabled = state.phoneButtonEnabled,
                    email = state.userTextEmail,
                    phone = state.userTextPhone
                )

                Spacer(Modifier.height(24.dp))

                LoginPrivacySection()
            }
        }
    }
}