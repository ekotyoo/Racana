package com.ekotyoo.racana.ui.main.listdestination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.ui.main.listdestination.model.ListDestinationEvent
import com.ekotyoo.racana.ui.main.listdestination.model.ListDestinationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDestinationViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ListDestinationState(destinations = getDummyDestination()))
    val state: StateFlow<ListDestinationState> = _state

    private val _eventChannel = Channel<ListDestinationEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onDestinationClicked(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(ListDestinationEvent.OnDestinationClicked(id))
        }
    }
}

fun getDummyDestination() : List<TravelDestination> {
    val dummy = mutableListOf<TravelDestination>()
    for ( i in 1..10) {
        dummy.add(
            TravelDestination(
                id = i,
                name = "Destinasi $i",
                description = "Deskripsi desinasi $i",
                city = "Kota $i"
            )
        )
    }
    return dummy
}