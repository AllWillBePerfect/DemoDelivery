package com.demo.delivery.utils

/**
 * Класс отображает email и телефон в форматированном виде над секцией ввода кода подтверждения.
 */
object CodeConfirmTextFormatter {
    fun phoneFormating(phone: String): String {
        return "+7 (${phone.substring(0, 3)}) ${phone[3]}**-**-${phone.substring(8, 10)}"
    }

    fun emailFormating(email: String): String {
        val beforePart = email.substringBefore('@')
        val afterPart = email.substringAfter('@')
        val beforeFormatterPart = when {
            beforePart.length >= 4 -> beforePart.substring(0, 3) + "*".repeat(beforePart.length - 3)
            beforePart.length == 1 -> beforePart.first().toString()
            else -> beforePart.substring(0, beforePart.length - 1) + "*"
        }

        val afterParts = afterPart.split(".")
        val afterFormatterPart = if (afterParts.size > 1) {
            "*".repeat(afterParts.dropLast(1).joinToString(".").length) + "." + afterParts.last()
        } else {
            afterPart
        }

        return "$beforeFormatterPart*$afterFormatterPart"
    }
}