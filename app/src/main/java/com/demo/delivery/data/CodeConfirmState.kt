package com.demo.delivery.data

/**
 * Класс данных, представляющий состояние экрана подтверждения кода.
 *
 * @property byEmailEnter флаг, указывающий, используется ли подтверждение по email.
 * @property buttonEnabled флаг, указывающий, активна ли кнопка подтверждения.
 * @property isError флаг, указывающий, есть ли ошибка при вводе кода.
 * @property timerValue текущее значение таймера, если он используется. Если null, таймер не используется.
 * @property otpValue значение одноразового пароля, введенного пользователем.
 */
data class CodeConfirmState(
    val byEmailEnter: Boolean = false,
    val buttonEnabled: Boolean = false,
    val isError: Boolean = false,
    val timerValue: Int? = null,
    val otpValue: String = "",
)