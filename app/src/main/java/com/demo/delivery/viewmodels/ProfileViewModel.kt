package com.demo.delivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.ui.screens.profile.models.ProfileState
import com.demo.delivery.utils.UserPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferencesUtils: UserPreferencesUtils
) : ViewModel() {

    private val _state = MutableLiveData(ProfileState())
    val state: LiveData<ProfileState> = _state

    init {
        viewModelScope.launch {

            userPreferencesUtils.userDataFlow.collect { userData ->
                _state.value = _state.value?.copy(
                    isAuthorized = userData.isLoggedIn,
                    userName = userData.name
                )
            }
        }
    }

}

