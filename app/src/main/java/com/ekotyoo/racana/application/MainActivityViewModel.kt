package com.ekotyoo.racana.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(authRepository: AuthRepository) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state: StateFlow<MainActivityState> = _state

    private val _eventChannel = Channel<MainActivityEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            authRepository.userData.collect {
                Timber.d(it.toString())
                if (it.token.isNullOrEmpty()) {
                    _eventChannel.send(MainActivityEvent.Unauthenticated)
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}

sealed class MainActivityEvent {
    object Unauthenticated : MainActivityEvent()
}

data class MainActivityState(
    val isLoading: Boolean = true,
)