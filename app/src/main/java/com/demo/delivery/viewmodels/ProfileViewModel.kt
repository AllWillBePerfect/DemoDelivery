package com.demo.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.data.ProfileState
import com.demo.delivery.utils.UserPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel для экрана профиля пользователя, где отображается его имя, а также присутствуют кнопки,
 * которые никуда не ведут.
 *
 * @property userPreferencesUtils класс для работы с данными пользователя.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferencesUtils: UserPreferencesUtils
) : ViewModel() {

    /**
     * StateFlow, который содержит текущее состояние профиля пользователя.
     * Он обновляется при изменении данных пользователя.
     *
     * @return [ProfileState] информация о том, авторизован ли пользователь и его имя.
     */
    val state = userPreferencesUtils.userDataFlow.map {
        ProfileState(
            isLoggedIn = it.isLoggedIn,
            userName = it.name
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ProfileState())

}

