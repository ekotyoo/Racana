package com.ekotyoo.racana.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state

    private val _eventChannel = Channel<ProfileScreenEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    //Assign Dummy Data
    init {
        _state.value = _state.value.copy(
            profilePictureUrl = "https://picsum.photos/200/300",
            nameTextFieldValue = "Ini Nama Pengguna",
            emailTextFieldValue = "email.pengguna@gmail.com",
            isPremium = true
        )
    }

    fun onMyPlanButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(ProfileScreenEvent.NavigateToMyPlan)
        }
    }

    fun onFavoriteDestinationButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(ProfileScreenEvent.NavigateToFavoriteDestination)
        }
    }

    fun onSettingsButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(ProfileScreenEvent.NavigateToSettings)
        }
    }

    fun onLogOutButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(ProfileScreenEvent.LogOut)
        }
    }
}

data class ProfileScreenState(
    val profilePictureUrl: String ="",
    val nameTextFieldValue: String = "",
    val emailTextFieldValue: String = "",
    val isPremium: Boolean = false
)

sealed class ProfileScreenEvent {
    object NavigateToMyPlan : ProfileScreenEvent()
    object NavigateToFavoriteDestination: ProfileScreenEvent()
    object NavigateToSettings: ProfileScreenEvent()
    object LogOut : ProfileScreenEvent()
    object BackButtonPressed : ProfileScreenEvent()
}