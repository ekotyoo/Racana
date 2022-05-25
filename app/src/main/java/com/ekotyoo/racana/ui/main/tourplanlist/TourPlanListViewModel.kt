package com.ekotyoo.racana.ui.main.tourplanlist

import androidx.lifecycle.ViewModel
import com.ekotyoo.racana.ui.home.tour_plan_list.model.TourPlanListEvent
import com.ekotyoo.racana.ui.home.tour_plan_list.model.TourPlanListState
import com.ekotyoo.racana.ui.home.tour_plan_list.model.getDummyPlan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class TourPlanListViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TourPlanListState(tourPlanItem = getDummyPlan()))
    val state: StateFlow<TourPlanListState> = _state

    private val _eventChannel = Channel<TourPlanListEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

}