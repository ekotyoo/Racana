package com.ekotyoo.racana.ui.main.tourplanmap

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ekotyoo.racana.ui.destinations.TourPlanMapScreenDestination
import com.ekotyoo.racana.ui.main.tourplanmap.model.TourPlanMapState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class TourPlanMapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(TourPlanMapState())
    val state: StateFlow<TourPlanMapState> = _state

    init {
        val args = TourPlanMapScreenDestination.argsFrom(savedStateHandle)
        _state.update {
            it.copy(tourPlan = args.tourPlan)
        }
    }

    fun onDateSelected(value: Int) {
        _state.update {
            it.copy(selectedDate = value)
        }
    }
}