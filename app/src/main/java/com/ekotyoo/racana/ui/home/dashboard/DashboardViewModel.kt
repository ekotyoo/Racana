package com.ekotyoo.racana.ui.home.dashboard

import androidx.lifecycle.ViewModel
import com.ekotyoo.racana.ui.home.dashboard.model.DashboardEvent
import com.ekotyoo.racana.ui.home.dashboard.model.DashboardState
import com.ekotyoo.racana.ui.home.dashboard.model.getDummyDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(DashboardState(destinations = getDummyDestination()))
    val state: StateFlow<DashboardState> = _state

    private val _eventChannel = Channel<DashboardEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()
}

