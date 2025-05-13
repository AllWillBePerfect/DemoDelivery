package com.demo.delivery.refactoring.components.codeconfirm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.delivery.R
import com.demo.delivery.refactoring.ui.theme.DeliveryTheme
import com.demo.delivery.refactoring.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.refactoring.ui.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.refactoring.viewmodels.CodeConfirmAction
import com.demo.delivery.refactoring.viewmodels.CodeConfirmViewModel

@Composable
fun CodeConfirmOtpSectionV3(
    timerValue: Int?,
    isError: Boolean,
    buttonEnabled: Boolean,
    onAction: (CodeConfirmAction) -> Unit
) {
    val otpLength = 4
    val otpValues = remember { mutableStateListOf("", "", "", "") }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }

    val keyboardController = LocalSoftwareKeyboardController.current

    val errorTextModifier = if (isError) Modifier.alpha(1f) else Modifier.alpha(0f)
    val timerTime = 60

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
        ) {
            for (i in 0 until otpLength) {
                TextField(
                    enabled = !buttonEnabled,
                    value = otpValues[i],
                    isError = isError,
                    onValueChange = { value ->
                        if (isError) onAction(CodeConfirmAction.HideError)
                        if (value.length <= 1) {
                            otpValues[i] = value
                            //  переход к следующей ячейке, если текущая заполнена
                            if (value.isNotEmpty() && i < otpLength - 1) {
                                focusRequesters[i + 1].requestFocus()
                            }

                            // если все цифры введены
                            if (otpValues.all { it.isNotEmpty() }) {
                                // скрываем клавиатуру
                                keyboardController?.hide()
                                // собираем код в строку
                                val code = otpValues.joinToString("")
                                // если код не правильный
                                if (code != CodeConfirmViewModel.codeConstant) {
                                    // показываем ошибку
                                    onAction(CodeConfirmAction.CleanCode)
                                    // очищаем все поля
                                    otpValues.forEachIndexed { index, value ->
                                        otpValues[index] = ""
                                    }
                                    // возвращаем фокус на первое поле
                                    focusRequesters.first().requestFocus()
                                } else {
                                    // иначе отправляем код
                                    onAction(CodeConfirmAction.InputCode(code))
                                }
                            }


                        }
                    },
                    modifier = Modifier
                        .size(60.dp)
                        .focusRequester(focusRequesters[i])
                        .onFocusChanged { focusState ->
                            //Пункт 7.	Код можно вводить только в порядке слева->направо, а стирать справа->налево.
                            // То есть, когда все цифры введены, нажатие на средние две цифры приводит к тому, что фокус ввода помещается на последнюю(4) цифру.
                            // Если не одной цифры не введено, нажатие приводит к фокусу на первую(1) цифру;
                            if (focusState.isFocused) {
                                val firstEmptyIndex = otpValues.indexOfFirst { it.isEmpty() }
                                    .let { if (it == -1) otpLength else it }

                                val focusIndex = when {
                                    // Все поля пусты — фокус в самое начало
                                    otpValues.all { it.isEmpty() } -> 0

                                    // Все поля заполнены — фокус в конец
                                    otpValues.all { it.isNotEmpty() } -> otpLength - 1

                                    // Текущая ячейка заполнена и есть пустая — перейти в первое пустое
                                    otpValues[i].isNotEmpty() && otpValues.any { it.isEmpty() } -> firstEmptyIndex

                                    // Текущая ячейка до первой пустой — оставить фокус
                                    i <= firstEmptyIndex -> i

                                    // Иначе — тоже на ближайшую пустую
                                    else -> firstEmptyIndex
                                }

                                if (focusIndex != i) {
                                    focusRequesters[focusIndex.coerceAtMost(otpLength - 1)].requestFocus()
                                }
                            }
                        },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            modifier = errorTextModifier,
            text = stringResource(R.string.code_confirm_error_code),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error,
            fontSize = 11.sp,
            lineHeight = 16.sp
        )

        Spacer(Modifier.height(24.dp))


        // Пункт 8.	Таймер на экране просто делает отчет,
        // по истечению таймера появляется кнопка "отправить еще раз" и снова запускается таймер;
        if (timerValue == null) {
            TextButton(onClick = { onAction(CodeConfirmAction.StartTimer(timerTime)) }) {
                Text(text = stringResource(R.string.code_confirm_send_code_again))
            }
        } else {
            Text(
                modifier = Modifier.alpha(0.6f),
                text = "Отправить код через $timerValue секунд",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }


    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun CodeConfirmOtpSectionV3Preview() = DeliveryTheme {
    CodeConfirmOtpSectionV3(
        timerValue = null,
        isError = false,
        buttonEnabled = false,
        onAction = {}
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun CodeConfirmOtpSectionV3PreviewIsError() = DeliveryTheme {
    CodeConfirmOtpSectionV3(
        timerValue = null,
        isError = true,
        buttonEnabled = false,
        onAction = {}

    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun CodeConfirmOtpSectionV3PreviewTimerIsRunning() = DeliveryTheme {
    CodeConfirmOtpSectionV3(
        timerValue = 120,
        isError = false,
        buttonEnabled = false,

        onAction = {}

    )
}


