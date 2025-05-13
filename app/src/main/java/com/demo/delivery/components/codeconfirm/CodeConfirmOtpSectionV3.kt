package com.demo.delivery.components.codeconfirm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.delivery.R
import com.demo.delivery.ui.screens.codeconfirm.models.CodeConfirmAction
import com.demo.delivery.ui.theme.DeliveryTheme
import com.demo.delivery.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_LIGHT
import com.demo.delivery.viewmodels.CodeConfirmViewModel

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

@Composable
fun CodeConfirmOtpSectionV4(

) {

    val otpLength = 4

    val otpValues =
        remember { mutableStateListOf(*Array(otpLength) { "" }) }
    val focusRequesters = List(otpLength) { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
        ) {
            otpValues.forEachIndexed { index, value ->
                TextField(
                    modifier = Modifier
                        .size(60.dp)
                        .focusRequester(focusRequesters[index])
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Backspace) {
                                if (otpValues[index].isEmpty() && index > 0) {
                                    otpValues[index] = ""
                                    focusRequesters[index - 1].requestFocus()
                                } else {
                                    otpValues[index] = ""
                                }
                                true
                            } else {
                                false
                            }

                        }
                    /*.onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            val firstEmptyIndex = otpValues.indexOfFirst { it.isEmpty() }
                                .let { if (it == -1) otpLength else it }

                            val focusIndex = when {
                                otpValues.all { it.isEmpty() } -> 0
                                otpValues.all { it.isNotEmpty() } -> otpLength - 1
                                otpValues[index].isNotEmpty() && otpValues.any { it.isEmpty() } -> firstEmptyIndex
                                index <= firstEmptyIndex -> index
                                else -> firstEmptyIndex
                            }

                            if (focusIndex != index) {
                                focusRequesters[focusIndex.coerceAtMost(otpLength - 1)].requestFocus()
                            }
                        }
                    }*/,
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.length <= 1) {
                            otpValues[index] = newValue
                            if (newValue.isNotEmpty()) {
                                if (index < otpLength - 1) {
                                    focusRequesters[index + 1].requestFocus()
                                } else {
                                    keyboardController?.hide()
                                }
                            }
                        } else {
                            if (index < otpLength - 1) focusRequesters[index + 1].requestFocus()
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
    }

    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun CodeConfirmOtpSectionV4Preview() = DeliveryTheme {
    CodeConfirmOtpSectionV4()
}

@Composable
fun OtpTextField(
    otpValue: String,
    isError: Boolean,
    onAction: (CodeConfirmAction) -> Unit,
    otpCount: Int = 4,
) {

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = TextFieldValue(otpValue, selection = TextRange(otpValue.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
//                otpValue = it.text
                onAction(CodeConfirmAction.InputCode(it.text))
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpValue,
                        isError = isError,
                    )
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    isError: Boolean
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }


    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isFocused) 3.dp else 2.dp)
                .background(
                    color = if (isFocused) MaterialTheme.colorScheme.primary
                    else if (isError) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.1f
                    )
                )
                .align(Alignment.BottomCenter)
        )
    }

}


@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 4,
    onOtpTextChange: (String) -> Unit,
    containerSize: Dp = 64.dp,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    focusedContainerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.outline,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
) {
    val focusRequesters = remember { List(otpCount) { FocusRequester() } }
    val textFieldValues = remember { List(otpCount) { mutableStateOf(TextFieldValue("")) } }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        repeat(otpCount) { index ->
            val isFocused = otpText.length == index
            val char = when {
                index >= otpText.length -> ""
                else -> otpText[index].toString()
            }

            Box(
                modifier = Modifier
                    .size(containerSize)
                    .background(
                        color = if (isFocused) focusedContainerColor else containerColor,
                        shape = MaterialTheme.shapes.medium
                    )
                    .drawBehind {
                        drawRoundRect(
                            color = if (isFocused) focusedBorderColor else unfocusedBorderColor,
                            style = Stroke(width = 2f)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = textFieldValues[index].value,
                    onValueChange = { newValue ->
                        if (newValue.text.length <= 1) {
                            textFieldValues[index].value = newValue
                            if (newValue.text.isEmpty()) {
                                // Handle backspace
                                if (otpText.isNotEmpty()) {
                                    val newOtpText = otpText.dropLast(1)
                                    onOtpTextChange(newOtpText)
                                    if (newOtpText.isNotEmpty()) {
                                        focusRequesters[newOtpText.lastIndex].requestFocus()
                                    }
                                }
                            } else {
                                // Handle new character
                                if (index < otpCount) {
                                    val newOtpText = if (index < otpText.length) {
                                        otpText.substring(
                                            0,
                                            index
                                        ) + newValue.text + otpText.substring(index + 1)
                                    } else {
                                        otpText + newValue.text
                                    }
                                    onOtpTextChange(newOtpText.take(otpCount))
                                    if (newOtpText.length < otpCount) {
                                        focusRequesters[newOtpText.length].requestFocus()
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .focusRequester(focusRequesters[index])
                        .align(Alignment.Center),
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    cursorBrush = SolidColor(cursorColor),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Handle OTP verification
                        }
                    ),
                    singleLine = true,
                    maxLines = 1,
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(containerSize)
                        ) {
                            if (char.isEmpty()) {
                                Text(
                                    text = "○",
                                    color = unfocusedBorderColor,
                                    fontSize = 24.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun OtpInput(
    modifier: Modifier = Modifier,
    onOtpComplete: (String) -> Unit = {}
) {
    var otp by remember { mutableStateOf("") }
    val focusRequesters = List(4) { remember { FocusRequester() } }
    val localFocusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0 until 4) {
            TextField(
                value = otp.getOrNull(i)?.toString() ?: "",

                onValueChange = { newChar ->
                    if (newChar.length > 1) return@TextField
                    val current = otp.padEnd(4, ' ')
                    val updated = buildString {
                        append(current.substring(0, i))
                        append(newChar)
                        if (i + 1 < 4) {
                            append(current.substring(i + 1))
                        }
                    }.trimEnd()

                    otp = updated.take(4)

                    if (newChar.isNotEmpty()) {
                        if (i < 3) {
                            focusRequesters[i + 1].requestFocus()
                        } else {
                            localFocusManager.clearFocus()
                            if (otp.length == 4) {
                                onOtpComplete(otp)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .width(60.dp)
                    .onKeyEvent { keyEvent ->


                        if (keyEvent.key == Key.Backspace) {
                            val current = otp
                            val charAtIndex = current.getOrNull(i)

                            if (i > 0 && (charAtIndex == null || charAtIndex == ' ')) {
                                otp = current.removeRange(i - 1, i).padEnd(4, ' ')
                                focusRequesters[i - 1].requestFocus()
                            } else if (i < current.length) {
                                otp = current.removeRange(i, i + 1).padEnd(4, ' ')
                            }
                            true
                        } else {
                            false
                        }
                    }
                    .focusRequester(focusRequesters[i]),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (i == 3) ImeAction.Done else ImeAction.Next
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
        }
    }

    // Автофокус на первом поле при открытии
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}

@Composable
fun OtpCodeField(
    length: Int = 4,
    onOtpComplete: (String) -> Unit
) {
    val focusRequesters = List(length) { FocusRequester() }
    val code = remember { mutableStateListOf(*Array(length) { "" }) }
    val previousCode = remember { mutableStateListOf(*Array(length) { "" }) } // Копия для сравнения

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0 until length) {
            val value = code[i]

            TextField(
                value = value,
                onValueChange = { newValue ->
                    // Проверяем, изменился ли код (если был удалён символ)
                    val isCodeChanged = code[i] != newValue

                    // При вводе цифры
                    if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                        code[i] = newValue
                        if (newValue.isNotEmpty() && i < length - 1) {
                            focusRequesters[i + 1].requestFocus()
                        }
                    }
                    // При удалении символа
                    if (newValue.isEmpty()) {
                        code[i] = ""
                        if (i > 0) {
                            focusRequesters[i - 1].requestFocus()
                        }
                    }

                    // Обновляем состояние предыдущего кода
                    if (isCodeChanged) {
                        previousCode[i] = newValue
                    }

                    if (code.all { it.isNotEmpty() }) {
                        onOtpComplete(code.joinToString(""))
                    }
                },
                modifier = Modifier
                    .width(48.dp)
                    .onFocusChanged { state ->
                        // Проверяем, был ли удалён символ
                        if (previousCode[i] != code[i]) {
                            // Если был удалён символ, не выполняем код в onFocusChanged
                            return@onFocusChanged
                        }

                        if (state.isFocused) {
                            // Перенос фокуса на первую пустую ячейку или на последнюю заполненную
                            val firstEmpty = code.indexOfFirst { it.isEmpty() }
                            if (firstEmpty == -1 && i != code.lastIndex) {
                                focusRequesters.last().requestFocus()
                            } else if (firstEmpty != -1 && i != firstEmpty) {
                                focusRequesters[firstEmpty].requestFocus()
                            }
                        }
                    }
                    .focusRequester(focusRequesters[i])
                    .focusProperties {
                        next = FocusRequester.Default
                        previous = FocusRequester.Default
                    },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        if (i < length - 1) {
                            focusRequesters[i + 1].requestFocus()
                        }
                    }
                )
            )
        }
    }
}





