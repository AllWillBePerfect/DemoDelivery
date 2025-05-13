package com.demo.delivery.components.codeconfirm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.demo.delivery.R
import com.demo.delivery.ui.theme.DeliveryTheme
import com.demo.delivery.ui.theme.PREVIEW_DEVICE
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_DARK
import com.demo.delivery.ui.theme.PREVIEW_UI_MODE_LIGHT

@Composable
fun CodeConfirmBottomSection(
    byEmailEnter: Boolean
) {

    val notSentText = if (byEmailEnter)
        stringResource(R.string.code_confirm_email_not_sent)
    else
        stringResource(R.string.code_confirm_sms_not_sent)

    val writeCorrectText = if (byEmailEnter)
        stringResource(R.string.code_confirm_email_write_correct)
    else
        stringResource(R.string.code_confirm_sms_write_correct)



    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.alpha(0.3f),
            text = notSentText,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.W500,
            lineHeight = 20.sp
        )

        Text(
            modifier = Modifier.alpha(0.3f),
            text = writeCorrectText,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun PreviewCodeConfirmBottomSectionEnterByEmail() = DeliveryTheme {
    CodeConfirmBottomSection(
        byEmailEnter = true
    )
}

@Preview(uiMode = PREVIEW_UI_MODE_DARK, device = PREVIEW_DEVICE)
@Preview(uiMode = PREVIEW_UI_MODE_LIGHT, device = PREVIEW_DEVICE)
@Composable
fun PreviewCodeConfirmBottomSectionEnterByPhone() = DeliveryTheme {
    CodeConfirmBottomSection(
        byEmailEnter = false
    )
}