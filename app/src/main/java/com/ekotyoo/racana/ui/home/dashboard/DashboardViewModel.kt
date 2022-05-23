package com.ekotyoo.racana.ui.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.data.repository.AuthRepository
import com.ekotyoo.racana.ui.home.dashboard.model.DashboardEvent
import com.ekotyoo.racana.ui.home.dashboard.model.DashboardState
import com.ekotyoo.racana.ui.home.dashboard.model.getDummyDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState(destinations = getDummyDestination()))
    val state: StateFlow<DashboardState> = _state

    private val _eventChannel = Channel<DashboardEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            authRepository.userData.collect { user ->
                _state.update { it.copy(user = user) }
            }
        }
    }
}

