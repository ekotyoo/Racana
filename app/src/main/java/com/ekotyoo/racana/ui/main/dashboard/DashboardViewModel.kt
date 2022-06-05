package com.ekotyoo.racana.ui.main.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.AuthRepository
import com.ekotyoo.racana.data.repository.DestinationRepository
import com.ekotyoo.racana.ui.main.dashboard.model.DashboardEvent
import com.ekotyoo.racana.ui.main.dashboard.model.DashboardState
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
class DashboardViewModel @Inject constructor(
    authRepository: AuthRepository,
    private val destinationRepository: DestinationRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    private val _eventChannel = Channel<DashboardEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        getTopDestinations()
        viewModelScope.launch {
            authRepository.userData.collect { user ->
                _state.update { it.copy(user = user) }
            }
        }
    }

    fun onDestinationClicked(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(DashboardEvent.NavigateToDetailDestination(id))
        }
    }

    private fun getTopDestinations() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = destinationRepository.getTopDestinations()) {
                is Result.Error -> {
                    Timber.d("Error")
                }
                is Result.Success -> {
                    _state.update { it.copy(destinations = result.value) }
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}

