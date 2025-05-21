package com.demo.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.utils.UserPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel для проверки состояния авторизации пользователя.
 *
 * @property userPreferencesUtils класс для работы с данными пользователя.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesUtils: UserPreferencesUtils
) : ViewModel() {

    /**
     * StateFlow, который содержит текущее состояние авторизации.
     * Он обновляется при изменении данных пользователя.
     *
     * @return [Boolean?] информация о том, авторизован ли пользователь. Изначально возвращает [null],
     * чтобы не отображать интерфейс до того, как данные будут загружены.
     */
    val isLoggedIn = userPreferencesUtils.userDataFlow.map { it.isLoggedIn }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

}