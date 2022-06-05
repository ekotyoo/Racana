package com.ekotyoo.racana.ui.main.tourplandetailsaved

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.TourPlanRepository
import com.ekotyoo.racana.ui.destinations.TourPlanDetailSavedScreenDestination
import com.ekotyoo.racana.ui.main.tourplandetailsaved.model.TourPlanDetailSavedEvent
import com.ekotyoo.racana.ui.main.tourplandetailsaved.model.TourPlanDetailSavedState
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
class TourPlanDetailSavedViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tourPlanRepository: TourPlanRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TourPlanDetailSavedState())
    val state: StateFlow<TourPlanDetailSavedState> = _state

    private val _eventChannel = Channel<TourPlanDetailSavedEvent>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        val args = TourPlanDetailSavedScreenDestination.argsFrom(savedStateHandle)
        val tourPlan = args.tourPlan
        if (tourPlan.id != null) {
            getTourPlanById(tourPlan.id.toInt())
        }
    }

    fun onDateSelected(value: Int) {
        _state.update { it.copy(selectedDate = value) }
    }

    fun navigateToDestinationDetail(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(TourPlanDetailSavedEvent.NavigateToDestinationDetail(id))
        }
    }

    fun startTourButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(TourPlanDetailSavedEvent.StartTourButtonClicked)
        }
    }

    fun deleteDestinationButtonClicked(destinationId: Int) {
        deleteTourPlanDateDestination(destinationId)
    }

    private fun deleteTourPlanDateDestination(destinationId: Int) {
        viewModelScope.launch {
            val dateId = _state.value.tourPlan.dailyList[_state.value.selectedDate].id
            when(val result = tourPlanRepository.deleteTourPlanDateDestination(dateId, destinationId)) {
                is Result.Success -> {
                    val id = _state.value.tourPlan.id?.toInt()
                    if (id != null) {
                        getTourPlanById(id)
                    }
                    _eventChannel.send(TourPlanDetailSavedEvent.DeleteDestinationSuccess)
                }
                is Result.Error -> {
                    _eventChannel.send(TourPlanDetailSavedEvent.DeleteDestinationError)
                }
            }
        }
    }

    private fun getTourPlanById(id: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = tourPlanRepository.getSavedTourPlanById(id)) {
                is Result.Success -> {
                    _state.update { it.copy(tourPlan = result.value) }
                }
                is Result.Error -> {
                    // TODO: Handle error 
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}