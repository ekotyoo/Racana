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
import com.ekotyoo.racana.ui.main.destinationdetail.model.getDummyDetailDestination
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _eventChannel = Channel<DestinationDetailEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        val args = DestinationDetailScreenDestination.argsFrom(savedStateHandle)
        getDestination(args.categoryId)
    }

    fun onFavoriteButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(DestinationDetailEvent.OnFavoriteButtonClicked)
        }
    }

    private fun getDestination(id: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = destinationRepository.getDestinationById(id)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(destination = DestinationDetail(
                            name = result.value.name,
                            imageUrl = result.value.imageUrl,
                            openCloseTime = "--",
                            address = result.value.location,
                            ticketPrice = result.value.expense,
                            description = result.value.brief,
                            lat = result.value.lat ?: .0,
                            lon = result.value.lon ?: .0
                        ))
                    }
                }
                is Result.Error -> {
                    // TODO: Handle error
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}