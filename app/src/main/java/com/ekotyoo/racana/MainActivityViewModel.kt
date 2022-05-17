package com.ekotyoo.racana

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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
                if (it.token.isNullOrEmpty()) {
                    _eventChannel.send(MainActivityEvent.Unauthenticated)
                }
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}

sealed class MainActivityEvent {
    object Unauthenticated : MainActivityEvent()
}

data class MainActivityState(
    val isLoading: Boolean = true,
)