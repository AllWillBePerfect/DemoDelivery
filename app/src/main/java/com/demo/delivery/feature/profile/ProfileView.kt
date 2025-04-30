package com.demo.delivery.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.delivery.R
import com.demo.delivery.core.theme.DeliveryTheme
import com.demo.delivery.core.theme.PREVIEW_DEVICE
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.core.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.feature.profile.view.ProfileAuthItem
import com.demo.delivery.feature.profile.view.ProfileUnableItem

@Composable
fun ProfileView(
    state: ProfileState
) {

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
                    isAuthorized = state.isAuthorized,
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


@Preview(
    uiMode = PREVIEW_UI_MODE_DARK,
    device = PREVIEW_DEVICE
)
@Preview(
    uiMode = PREVIEW_UI_MODE_LIGHT,
    device = PREVIEW_DEVICE
)
@Composable
fun ProfileViewPreview() = DeliveryTheme {
    ProfileView(ProfileState(
    ))
}