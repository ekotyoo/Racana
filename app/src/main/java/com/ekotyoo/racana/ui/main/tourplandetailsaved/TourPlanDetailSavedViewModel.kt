package com.ekotyoo.racana.ui.main.tourplandetailsaved

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.TourPlanRepository
import com.ekotyoo.racana.ui.destinations.TourPlanScreenDestination
import com.ekotyoo.racana.ui.main.tourplandetailsaved.model.TourPlanDetailSavedEvent
import com.ekotyoo.racana.ui.main.tourplandetailsaved.model.TourPlanDetailSavedState
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourPlanDetailSavedViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TourPlanDetailSavedState())
    val state: StateFlow<TourPlanDetailSavedState> = _state

    private val _eventChannel = Channel<TourPlanDetailSavedEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onDateSelected(value: Int) {
        _state.update { it.copy(selectedDate = value) }
    }

    fun navigateToDestinationDetail() {
        viewModelScope.launch {
            _eventChannel.send(TourPlanDetailSavedEvent.NavigateToDestinationDetail)
        }
    }

    fun startTourButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(TourPlanDetailSavedEvent.StartTourButtonClicked)
        }
    }
}