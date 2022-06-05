package com.ekotyoo.racana.ui.main.favoritedestination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.DestinationRepository
import com.ekotyoo.racana.ui.main.favoritedestination.model.FavoriteDestinationEvent
import com.ekotyoo.racana.ui.main.favoritedestination.model.FavoriteDestinationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDestinationViewModel @Inject constructor(
    private val destinationRepository: DestinationRepository
) : ViewModel() {
    private val _state = MutableStateFlow(FavoriteDestinationState())
    val state: StateFlow<FavoriteDestinationState> = _state

    private val _eventChannel = Channel<FavoriteDestinationEvent>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        getFavoriteDestinations()
    }

    private fun getFavoriteDestinations() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = destinationRepository.getFavoriteDestinations()) {
                is Result.Success -> {
                    _state.update { it.copy(destinations = result.value) }
                }
                is Result.Error -> {
                    _eventChannel.send(FavoriteDestinationEvent.GetFavoriteDestinationsError)
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onDestinationClicked(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(FavoriteDestinationEvent.NavigateToDetailDestination(id))
        }
    }
}