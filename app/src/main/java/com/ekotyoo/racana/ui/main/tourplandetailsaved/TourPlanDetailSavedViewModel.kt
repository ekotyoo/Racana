package com.ekotyoo.racana.ui.main.tourplandetailsaved

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.ui.destinations.TourPlanDetailSavedScreenDestination
import com.ekotyoo.racana.ui.main.tourplandetailsaved.model.TourPlanDetailSavedEvent
import com.ekotyoo.racana.ui.main.tourplandetailsaved.model.TourPlanDetailSavedState
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel() {

    private val _state = MutableStateFlow(TourPlanDetailSavedState())
    val state: StateFlow<TourPlanDetailSavedState> = _state

    private val _eventChannel = Channel<TourPlanDetailSavedEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        val args = TourPlanDetailSavedScreenDestination.argsFrom(savedStateHandle)
        val tourPlan = args.tourPlan
        _state.update { it.copy(tourPlan = tourPlan) }
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

    fun deleteDestinationButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(TourPlanDetailSavedEvent.DeleteDestinationButtonClicked)
        }
    }
}