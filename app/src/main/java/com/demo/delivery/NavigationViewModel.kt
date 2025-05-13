package com.demo.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.delivery.refactoring.utils.UserPreferencesUtils
import com.demo.delivery.refactoring.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 */
@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val userPreferencesUtils: UserPreferencesUtils
) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _navigateEffect = MutableLiveData<Event<String>>(null)
    val navigateEffect: LiveData<Event<String>> = _navigateEffect

    init {
        loadData()

    }

    fun loadData() {
        viewModelScope.launch {
            userPreferencesUtils.userIsLoggedIn().collect {
                _isLoggedIn.value = it
            }
        }
    }

}