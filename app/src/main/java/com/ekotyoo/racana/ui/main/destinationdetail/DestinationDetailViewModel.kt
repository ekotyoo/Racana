package com.ekotyoo.racana.ui.main.destinationdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.DestinationRepository
import com.ekotyoo.racana.ui.destinations.DestinationDetailScreenDestination
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetail
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetailEvent
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetailState
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
class DestinationDetailViewModel @Inject constructor(
    private val destinationRepository: DestinationRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(DestinationDetailState())
    val state: StateFlow<DestinationDetailState> = _state

    private val _eventChannel = Channel<DestinationDetailEvent>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        val args = DestinationDetailScreenDestination.argsFrom(savedStateHandle)
        getDestination(args.categoryId)
    }

    fun onFavoriteButtonClicked(id: Int) {
        saveFavoriteDestination(id)
    }

    fun onUndoFavoriteButtonClicked(id: Int) {
        deleteFavoriteDestination(id)
    }

    private fun deleteFavoriteDestination(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isTogglingFavorite = true) }
            viewModelScope.launch {
                when (destinationRepository.deleteFavoriteDestination(id)) {
                    is Result.Success -> {
                        _state.update { it.copy(destination = _state.value.destination.copy(isFavorite = false)) }
                        _eventChannel.send(DestinationDetailEvent.UndoFavoriteDestinationSuccess)
                    }
                    is Result.Error -> {
                        _eventChannel.send(DestinationDetailEvent.UndoFavoriteDestinationError)
                    }
                }
                _state.update { it.copy(isTogglingFavorite = false) }
            }
        }
    }

    private fun saveFavoriteDestination(id: Int) {
        _state.update { it.copy(isTogglingFavorite = true) }
        viewModelScope.launch {
            when (destinationRepository.saveFavoriteDestination(id)) {
                is Result.Success -> {
                    _state.update { it.copy(destination = _state.value.destination.copy(isFavorite = true)) }
                    _eventChannel.send(DestinationDetailEvent.SaveFavoriteDestinationSuccess)
                }
                is Result.Error -> {
                    _eventChannel.send(DestinationDetailEvent.SaveFavoriteDestinationError)
                }
            }
            _state.update { it.copy(isTogglingFavorite = false) }
        }
    }

    private fun getDestination(id: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val favoriteDestinations = destinationRepository.getFavoriteDestinations()
            var isFavorite = false
            if (favoriteDestinations is Result.Success) {
                val destinations = favoriteDestinations.value
                isFavorite = destinations.any{
                    it.id == id
                }
            }
            when (val result = destinationRepository.getDestinationById(id)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(destination = DestinationDetail(
                            id = result.value.id,
                            name = result.value.name,
                            imageUrl = result.value.imageUrl,
                            openCloseTime = "--",
                            address = result.value.address,
                            ticketPrice = result.value.weekdayPrice.toLong(),
                            ticketPriceWeekend = result.value.weekendHolidayPrice.toLong(),
                            description = result.value.description,
                            isFavorite = isFavorite,
                            rating = result.value.rating,
                            lat = result.value.lat,
                            lon = result.value.lon
                        ))
                    }
                }
                is Result.Error -> {
                    _eventChannel.send(DestinationDetailEvent.GetDestinationDetailError)
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}