package com.demo.delivery.components.codeconfirm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.delivery.R
import com.demo.delivery.data.CodeConfirmAction
import com.demo.delivery.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun CodeConfirmTimerSection(
    timerValue: Int?,
    isError: Boolean,
    byEmailEnter: Boolean,
    onAction: (CodeConfirmAction) -> Unit
) {

    val errorTextModifier = remember(isError) {
        if (isError) Modifier.alpha(1f) else Modifier.alpha(0f)
    }
    val timerTime = remember { if (byEmailEnter) 120 else 60 }

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
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
fun PreviewCodeConfirmTimerSection() {
    MaterialTheme {
        CodeConfirmTimerSection(timerValue = 10, isError = false, byEmailEnter = false) {}
    }
}
