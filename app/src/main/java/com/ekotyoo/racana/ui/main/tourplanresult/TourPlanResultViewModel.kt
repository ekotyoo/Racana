package com.ekotyoo.racana.ui.main.tourplanresult

import androidx.lifecycle.ViewModel
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlan
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultEvent
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultState
import com.ekotyoo.racana.ui.main.tourplanresult.model.getDummyTourPlan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TourPlanResultViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TourPlanResultState())
    val state: StateFlow<TourPlanResultState> = _state

    private val _eventChannel = Channel<TourPlanResultEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        _state.update {
            it.copy(tourPlan = getDummyTourPlan())
        }
    }

    fun onDateSelected(value: Int) {
        _state.update { it.copy(selectedDate = value) }
    }
}