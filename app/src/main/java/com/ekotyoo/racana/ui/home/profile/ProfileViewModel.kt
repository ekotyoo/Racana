package com.ekotyoo.racana.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.AuthRepository
import com.ekotyoo.racana.ui.home.profile.model.ProfileEvent
import com.ekotyoo.racana.ui.home.profile.model.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _eventChannel = Channel<ProfileEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            authRepository.userData.collect { user ->
                _state.update {
                    it.copy(user = user)
                }
            }
        }
    }

    fun onMyPlanButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(ProfileEvent.NavigateToMyPlan)
        }
    }

    fun onFavoriteDestinationButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(ProfileEvent.NavigateToFavoriteDestination)
        }
    }

    fun onSettingsButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(ProfileEvent.NavigateToSettings)
        }
    }

    fun onLogOutButtonClicked() {
        viewModelScope.launch {
            when (authRepository.logout()) {
                is Result.Success -> _eventChannel.send(ProfileEvent.LogOutSuccess)
                is Result.Error -> _eventChannel.send(ProfileEvent.LogOutFailed)
            }
        }
    }
}