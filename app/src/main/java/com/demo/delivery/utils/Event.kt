package com.demo.delivery.utils

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

    /**
     * Возвращает содержимое, даже если оно уже было обработано.
     */
    fun requireValue(): T = content
}