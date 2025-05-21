package com.demo.delivery.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.delivery.R
import com.demo.delivery.components.profile.ProfileAuthItem
import com.demo.delivery.components.profile.ProfileUnableItem
import com.demo.delivery.data.ProfileState
import com.demo.delivery.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                ProfileAuthItem(
                    isAuthorized = state.isLoggedIn,
                    userName = state.userName
                )

                ProfileUnableItem(stringResource(R.string.profile_order_history))
                ProfileUnableItem(stringResource(R.string.profile_questions_and_answers))
                ProfileUnableItem(stringResource(R.string.profile_politics))
                ProfileUnableItem(stringResource(R.string.profile_rules))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.profile_version),
                    fontSize = 12.sp
                )

            }
        }
    }
}