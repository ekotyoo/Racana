package com.ekotyoo.racana.ui.home.main

import androidx.lifecycle.ViewModel
import com.ekotyoo.racana.ui.login.LoginScreenEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState(destinations = getDummyDestination()))
    val state: StateFlow<MainScreenState> = _state

    private val _eventChannel = Channel<LoginScreenEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onSearchTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(searchTextFieldValue = value)
    }

    fun onClearSearchTextField() {
        _state.value = _state.value.copy(searchTextFieldValue = "")
    }

    fun onSearch() {
        // TODO: Implement search functionality
    }
}

fun getDummyDestination() =
    List(10) { TravelDestination("Name $it", "https://picsum.photos/200/300", "Location $it") }

data class MainScreenState(
    val searchTextFieldValue: String = "",
    val destinations: List<TravelDestination> = listOf()
)