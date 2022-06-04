package com.ekotyoo.racana.ui.main.tourplanlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.repository.TourPlanRepository
import com.ekotyoo.racana.ui.main.tourplanlist.model.TourPlanListEvent
import com.ekotyoo.racana.ui.main.tourplanlist.model.TourPlanListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourPlanListViewModel @Inject constructor(
    tourPlanRepository: TourPlanRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TourPlanListState(tourPlanList = emptyList()))
    val state: StateFlow<TourPlanListState> = _state

    private val _eventChannel = Channel<TourPlanListEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = tourPlanRepository.getSavedTourPlan()) {
                is Result.Success -> {
                    _state.update { it.copy(tourPlanList = result.value) }
                }
                is Result.Error -> {
                    // TODO: Handle error 
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

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