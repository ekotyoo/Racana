package com.ekotyoo.racana.ui.main.destinationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetailEvent
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetailState
import com.ekotyoo.racana.ui.main.destinationdetail.model.getDummyDetailDestination
import com.ekotyoo.racana.ui.main.profile.model.ProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationDetailViewModel @Inject constructor() : ViewModel(){
    private val _state = MutableStateFlow(DestinationDetailState(destination = getDummyDetailDestination()))
    val state: StateFlow<DestinationDetailState> = _state

    private val _eventChannel = Channel<DestinationDetailEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onBackButtonPressed() {
        viewModelScope.launch {
            _eventChannel.send(DestinationDetailEvent.OnBackButtonPressed)
        }
    }

    fun onFavoriteButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(DestinationDetailEvent.OnFavoriteButtonClicked)
        }
    }

}