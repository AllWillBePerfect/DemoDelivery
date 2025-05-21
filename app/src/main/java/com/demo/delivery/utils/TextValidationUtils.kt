package com.demo.delivery.utils

import android.util.Patterns

/**
 * Класс, содержащий утилитарные функции для валидации текстовых данных.
 */
object TextValidationUtils {

    /**
     * Проверяет, является ли переданный email корректным.
     *
     * @param email строка, содержащая email для проверки.
     * @return [Boolean] true, если email корректен, иначе false.
     */
    fun validateEmail(email: String): Boolean =
        if (email.contains('@')) {
            val beforePart = email.substringBefore('@')
            // проверка на отсутствия точки перед @
            Patterns.EMAIL_ADDRESS.matcher(email).matches() && beforePart.last() != '.'
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()

        }

    /**
     * Проверяет, является ли переданный номер телефона корректным.
     *
     * @param phoneNumber строка, содержащая номер телефона для проверки.
     * @return [Boolean] true, если номер телефона корректен, иначе false.
     */
    fun validatePhoneNumber(phoneNumber: String): Boolean =
        phoneNumber.length == 10

}