package com.demo.delivery.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.core.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableLiveData(ProfileState())
    val state: LiveData<ProfileState> = _state

    init {
        viewModelScope.launch {

            userPreferences.userDataFlow.collect { userData ->
                _state.value = _state.value?.copy(
                    isAuthorized = userData.isLoggedIn,
                    userName = userData.name
                )
            }
        }
    }

}

data class ProfileState(
    val isAuthorized: Boolean = false,
    val userName: String = ""
)