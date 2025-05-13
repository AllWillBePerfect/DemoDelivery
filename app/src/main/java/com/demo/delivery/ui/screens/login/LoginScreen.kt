package com.demo.delivery.ui.screens.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.delivery.R
import com.demo.delivery.ui.navigation.AppScreens
import com.demo.delivery.ui.navigation.localNavHost
import com.demo.delivery.ui.screens.login.models.LoginAction
import com.demo.delivery.ui.screens.login.models.LoginState
import com.demo.delivery.utils.NanpVisualTransformation
import com.demo.delivery.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val navController = localNavHost.current

    val state by viewModel.state.observeAsState(LoginState())

    val buttonText =
        if (state.byEmailEnter)
            stringResource(R.string.login_request_email_code)
        else
            stringResource(R.string.login_request_sms_code)

    val buttonEnabledState =
        state.byEmailEnter && state.emailButtonEnabled || !state.byEmailEnter && state.phoneButtonEnabled


    val textButtonText =
        if (state.byEmailEnter)
            stringResource(R.string.login_enter_by_phone_button)
        else
            stringResource(R.string.login_enter_by_email)

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(220.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = stringResource(R.string.login_enter_icon),
                        modifier = Modifier.size(52.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        modifier = Modifier,
                        text = if (state.byEmailEnter) stringResource(R.string.login_enter_by_email_lowercase) else stringResource(
                            R.string.login_enter_by_phone
                        ),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.login_enter_to_get_bonuses),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                }

                Spacer(Modifier.height(54.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //Пункт3.	На полях ввода номера и почты обязательно должно быть форматирование
                    // и проверка корректного набора;

                    if (state.byEmailEnter) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.userTextEmail,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Done
                            ),
                            placeholder = { Text(text = stringResource(R.string.login_email_example)) },
                            singleLine = true,
                            onValueChange = {
                                viewModel.dispatchAction(
                                    LoginAction.UpdateUserTextEmail(
                                        it
                                    )
                                )
                            }
                        )
                    } else {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.userTextPhone,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Done
                            ),
                            placeholder = { Text(text = stringResource(R.string.login_phone_example)) },
                            //Итоговый ввод номера пользователя форматируется только для этого воля с помощью NanpVisualTransformation
                            visualTransformation = NanpVisualTransformation(),
                            singleLine = true,
                            onValueChange = {
                                // Ограничение на 10 символов в поле
                                if (it.length <= 10)
                                    viewModel.dispatchAction(LoginAction.UpdateUserTextPhone(it))
                            }
                        )
                    }

                    TextButton(onClick = { viewModel.dispatchAction(LoginAction.SwitchAuthMethod) }) {
                        Text(textButtonText)
                    }

                }
            }


            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        onClick = {
                            // Пункт 5.	После ввода корректного телефона/почты и нажатия на "Отправить код"
                            // state.byEmailEnter переход на экран ввода кода;
                            if (state.byEmailEnter) {
                                //если вход по почте, то отправляем аргументы для почты
                                navController.navigate(AppScreens.CodeConfirmEmail.createRoute(state.userTextEmail))
                            } else {
                                //если вход по телефону, то отправляем аргументы для телефона
                                navController.navigate(AppScreens.CodeConfirmPhone.createRoute(state.userTextPhone))

                            }
                        },
                        enabled = buttonEnabledState
                    ) {
                        Text(
                            text = buttonText,
                            color = Color.White,
                        )
                    }

                    TextButton(onClick = {
                        navController.navigate(AppScreens.Profile.route) {
                            popUpTo(AppScreens.Login.route) { inclusive = true }
                        }
                    }) {
                        Text(text = stringResource(R.string.login_skip))
                    }
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier,
                        text = buildAnnotatedString {
                            append(stringResource(R.string.login_privacy_first_part))
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(stringResource(R.string.login_privacy_second_part))
                            }
                        },
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

            }
        }
    }
}