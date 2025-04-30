package com.demo.delivery.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData(ProfileState())
    val state: LiveData<ProfileState> = _state

    fun setAuthorizedUser(name: String) {
        _state.value = ProfileState(isAuthorized = true, userName = name)
    }

    fun logout() {
        _state.value = ProfileState()
    }
}

data class ProfileState(
    val isAuthorized: Boolean = false,
    val userName: String = ""
)