package com.ekotyoo.racana.ui.favoritedestination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.ui.favoritedestination.model.FavoriteDestinationEvent
import com.ekotyoo.racana.ui.favoritedestination.model.FavoriteDestinationState
import com.ekotyoo.racana.ui.main.listdestination.getDummyDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDestinationViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(FavoriteDestinationState(destinations = getDummyDestination()))
    val state: StateFlow<FavoriteDestinationState> = _state

    private val _eventChannel = Channel<FavoriteDestinationEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onDestinationClicked(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(FavoriteDestinationEvent.OnDestinationClicked(id))
        }
    }
}