package com.demo.delivery.feature.login.formatter

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Форматирует итоговое представление введенного номера телефона в формате +7 (XXX) XXX-XX-XX
 *
 * Код взят с документации [Пример форматирования номера](https://developer.android.com/develop/ui/compose/quick-guides/content/auto-format-phone-number?hl=ru)
 */
class NanpVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 10) text.text.substring(0..9) else text.text

        var out = if (trimmed.isNotEmpty()) "+7 (" else ""

        for (i in trimmed.indices) {
            if (i == 3) out += ") "
            if (i == 6) out += "-"
            if (i == 8) out += "-"
            out += trimmed[i]
        }
        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }

    private val phoneNumberOffsetTranslator = object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
                0 -> 0                        // ничего не добавлено
                in 1..3 -> offset + 4         // +7 (
                in 4..6 -> offset + 6         // +7 (123)
                in 7..8 -> offset + 7         // +7 (123) 456-
                else -> offset + 8           // +7 (123) 456-78-
            }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {
                in 0..4 -> 0                      // до первой цифры, всё форматное
                in 5..8 -> offset - 4             // в блоке (123)
                in 9..11 -> offset - 6            // в блоке 456
                in 12..14 -> offset - 7           // в блоке 78
                else -> offset - 8
            }
    }
}