package com.ekotyoo.racana.ui.main.listdestination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.DestinationRepository
import com.ekotyoo.racana.ui.main.listdestination.model.ListDestinationEvent
import com.ekotyoo.racana.ui.main.listdestination.model.ListDestinationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDestinationViewModel @Inject constructor(
    private val destinationRepository: DestinationRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ListDestinationState())
    val state: StateFlow<ListDestinationState> = _state

    private val _eventChannel = Channel<ListDestinationEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        getDestinations()
    }

    private fun getDestinations() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when(val result = destinationRepository.getTopDestinations()) {
                is Result.Success -> {
                    _state.update { it.copy(destinations = result.value) }
                }
                is Result.Error -> {
                    _eventChannel.send(ListDestinationEvent.GetDestinationsError)
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onDestinationClicked(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(ListDestinationEvent.NavigateToDetailDestination(id))
        }
    }
}
