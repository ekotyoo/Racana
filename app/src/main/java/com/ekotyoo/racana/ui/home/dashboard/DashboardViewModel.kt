package com.ekotyoo.racana.ui.home.dashboard

import androidx.lifecycle.ViewModel
import com.ekotyoo.racana.ui.login.LoginScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState(destinations = getDummyDestination()))
    val state: StateFlow<MainScreenState> = _state

    private val _eventChannel = Channel<LoginScreenEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()
}

fun getDummyDestination() =
    List(10) { TravelDestination("Name $it", "https://picsum.photos/200/300", "Location $it") }

data class MainScreenState(
    val searchTextFieldValue: String = "",
    val destinations: List<TravelDestination> = listOf()
)