package com.ekotyoo.racana.ui.main.favoritedestination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.ui.main.favoritedestination.model.FavoriteDestinationEvent
import com.ekotyoo.racana.ui.main.favoritedestination.model.FavoriteDestinationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDestinationViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(FavoriteDestinationState())
    val state: StateFlow<FavoriteDestinationState> = _state

    private val _eventChannel = Channel<FavoriteDestinationEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onDestinationClicked(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(FavoriteDestinationEvent.OnDestinationClicked(id))
        }
    }
}