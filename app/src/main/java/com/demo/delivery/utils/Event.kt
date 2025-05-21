package com.demo.delivery.utils

/**
 * Класс-обертка для данных, которые должны быть обработаны только один раз.
 *
 * @param T тип содержимого, которое оборачивается.
 * @property content содержимое, которое оборачивается.
 */
open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Возвращает содержимое и отмечает его как обработанное.
     */
    fun singleValue(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}