package com.demo.delivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.ui.screens.codeconfirm.models.CodeConfirmAction
import com.demo.delivery.ui.screens.codeconfirm.models.CodeConfirmState
import com.demo.delivery.utils.Event
import com.demo.delivery.utils.UserPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeConfirmViewModel @Inject constructor(
    private val userPreferencesUtils: UserPreferencesUtils
) : ViewModel() {

    private val _state = MutableLiveData(CodeConfirmState())
    val state: LiveData<CodeConfirmState> = _state

    private val _navigateToProfileEffect = MutableLiveData<Event<Unit>>()
    val navigateToProfileEffect: LiveData<Event<Unit>> = _navigateToProfileEffect


    fun dispatchAction(action: CodeConfirmAction) {
        when (action) {
            is CodeConfirmAction.InputCode -> {
                _state.value = _state.value?.copy(otpValue = action.code)

                if (action.code.length < 4) {
                    _state.value = _state.value?.copy(
                        buttonEnabled = false,
                        isError = false
                    )
                    return
                }

                val validCode = action.code == codeConstant
                if (validCode) {
                    _state.value = _state.value?.copy(
                        buttonEnabled = true,
                        isError = false
                    )
                } else {
                    _state.value = _state.value?.copy(
                        buttonEnabled = false,
                        isError = true
                    )
                }
            }


            CodeConfirmAction.CleanCode -> {
                _state.value = _state.value?.copy(
                    isError = true
                )
            }

            CodeConfirmAction.HideError -> {
                _state.value = _state.value?.copy(
                    isError = false
                )
            }

            is CodeConfirmAction.StartTimer -> {
                startTimer(action.initialValue)
            }

            is CodeConfirmAction.AuthByEmail -> {
                viewModelScope.launch {
                    userPreferencesUtils.authorizeByEmail(action.email)
                    _navigateToProfileEffect.value = Event(Unit)
                }
            }

            is CodeConfirmAction.AuthByPhone -> {
                viewModelScope.launch {
                    userPreferencesUtils.authorizeByPhone(action.phone)
                    _navigateToProfileEffect.value = Event(Unit)
                }
            }
        }
    }


    /**
     * Запуск таймера. Если таймер завершился, `timerValue == null`
     */
    fun startTimer(initialValue: Int) {
        viewModelScope.launch {
            var currentValue = initialValue
            while (currentValue > 0) {
                _state.value = _state.value?.copy(timerValue = currentValue)
                delay(1000L)
                currentValue--
            }
            _state.value = _state.value?.copy(timerValue = null)
        }
    }


    companion object {
        //Пункт 6.	Код можно константы(например 1234)
        // как поле viewModel-класса и с ним набранное пользователем сравнивать.
        // Приложение не обращается к API;
        const val codeConstant = "1234"
    }
}



