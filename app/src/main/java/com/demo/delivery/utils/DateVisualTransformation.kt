package com.demo.delivery.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text

        var out = ""

        for (i in trimmed.indices) {
            if (i == 2) out += "/"
            if (i == 4) out += "/"
            out += trimmed[i]
        }
        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }

    private val phoneNumberOffsetTranslator = object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
                in 0..2 -> offset                     // dd
                in 3..4 -> offset + 1                 // dd/MM
                in 5..8 -> offset + 2                 // dd/MM/yyyy
                else -> offset + 3


                /*in 1..3 -> offset + 4         // +7 (
                in 4..6 -> offset + 6         // +7 (123)
                in 7..8 -> offset + 7         // +7 (123) 456-
                else -> offset + 8           // +7 (123) 456-78-*/
            }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {

                in 0..2 -> offset                     // d, d/, d/M
                in 3..5 -> offset - 1                 // MM part
                in 6..10 -> offset - 2                // yyyy part
                else -> 8                             // max original digits
                /*in 0..4 -> 0                      // до первой цифры, всё форматное
                in 5..8 -> offset - 4             // в блоке (123)
                in 9..11 -> offset - 6            // в блоке 456
                in 12..14 -> offset - 7           // в блоке 78
                else -> offset - 8*/
            }
    }
}