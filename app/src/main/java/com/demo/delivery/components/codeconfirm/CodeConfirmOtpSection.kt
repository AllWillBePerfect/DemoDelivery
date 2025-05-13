package com.demo.delivery.components.codeconfirm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.demo.delivery.ui.screens.codeconfirm.models.CodeConfirmAction

@Composable
fun CodeConfirmOtpSection(
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
                    InputField(
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
private fun InputField(
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






