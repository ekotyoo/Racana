package com.ekotyoo.racana.ui.main.tourplanlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.repository.TourPlanRepository
import com.ekotyoo.racana.ui.main.tourplanlist.model.TourPlanListEvent
import com.ekotyoo.racana.ui.main.tourplanlist.model.TourPlanListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourPlanListViewModel @Inject constructor(
    tourPlanRepository: TourPlanRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TourPlanListState(tourPlanList = emptyList()))
    val state: StateFlow<TourPlanListState> =
        _state.combine(tourPlanRepository.getSavedTourPlan()) { state, tourPlan ->
            state.copy(tourPlanList = tourPlan)
        }.stateIn(viewModelScope,
            SharingStarted.Eagerly,
            TourPlanListState(tourPlanList = emptyList()))


    private val _eventChannel = Channel<TourPlanListEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onTourPlanClicked(tourPlan: TourPlan) {
        viewModelScope.launch {
            _eventChannel.send(TourPlanListEvent.NavigateToTourPlanDetail(tourPlan))
        }
    }

    fun deletePlanButtonClicked() {
        viewModelScope.launch {
            _eventChannel.send(TourPlanListEvent.DeletePlanButtonClicked)
        }
    }
}