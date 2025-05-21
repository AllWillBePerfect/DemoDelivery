package com.demo.delivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.data.CodeConfirmAction
import com.demo.delivery.data.CodeConfirmState
import com.demo.delivery.utils.Event
import com.demo.delivery.utils.UserPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана подтверждения кода.
 *
 * @property userPreferencesUtils класс для работы с данными пользователя.
 */
@HiltViewModel
class CodeConfirmViewModel @Inject constructor(
    private val userPreferencesUtils: UserPreferencesUtils
) : ViewModel() {


    private val _state = MutableLiveData(CodeConfirmState())
    val state: LiveData<CodeConfirmState> = _state

    private val _navigateToProfileEffect = MutableLiveData<Event<Unit>>()
    val navigateToProfileEffect: LiveData<Event<Unit>> = _navigateToProfileEffect

    /**
     * Обрабатывает действия пользователя и обновляет состояние.
     *
     * @param action действие, которое нужно обработать.
     */
    fun dispatchAction(action: CodeConfirmAction) {
        when (action) {
            is CodeConfirmAction.InputCode -> {
                val currentState = _state.value?.copy(otpValue = action.code) ?: return

                if (action.code.length < 4) {
                    _state.value = (currentState.copy(
                        otpValue = action.code, buttonEnabled = false, isError = false
                    ))
                    return
                }

                val validCode = action.code == codeConstant
                if (validCode) {
                    _state.value = (currentState.copy(
                        otpValue = action.code, buttonEnabled = true, isError = false
                    ))
                } else {
                    _state.value = (currentState.copy(
                        otpValue = action.code, buttonEnabled = false, isError = true
                    ))
                }
            }


            CodeConfirmAction.CleanCode -> {
                _state.postValue(
                    _state.value?.copy(
                        isError = true
                    )
                )
            }

            CodeConfirmAction.HideError -> {
                _state.postValue(
                    _state.value?.copy(
                        isError = false
                    )
                )
            }

            is CodeConfirmAction.StartTimer -> {
                startTimer(action.initialValue)
            }

            is CodeConfirmAction.AuthByEmail -> {
                viewModelScope.launch {
                    userPreferencesUtils.authorizeByEmail(action.email)
                    _navigateToProfileEffect.postValue(Event(Unit))
                }
            }

            is CodeConfirmAction.AuthByPhone -> {
                viewModelScope.launch {
                    userPreferencesUtils.authorizeByPhone(action.phone)
                    _navigateToProfileEffect.postValue(Event(Unit))
                }
            }
        }
    }


    /**
     * Запускает таймер с заданным начальным значением.
     *
     * @param initialValue начальное значение таймера в секундах.
     * Таймер будет уменьшаться каждую секунду до нуля.
     * Если таймер завершился, `timerValue` будет равен `null`.
     */
    fun startTimer(initialValue: Int) {
        viewModelScope.launch {
            var currentValue = initialValue
            while (currentValue > 0) {
                _state.postValue(_state.value?.copy(timerValue = currentValue))
                delay(1000L)
                currentValue--
            }
            _state.postValue(_state.value?.copy(timerValue = null))
        }
    }


    companion object {
        //Пункт 6.	Код можно константы(например 1234)
        // как поле viewModel-класса и с ним набранное пользователем сравнивать.
        // Приложение не обращается к API;
        const val codeConstant = "1234"
    }
}



