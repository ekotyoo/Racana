package com.ekotyoo.racana.ui.main.tourplanresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.TourPlanRepository
import com.ekotyoo.racana.ui.destinations.TourPlanScreenDestination
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultEvent
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourPlanResultViewModel @Inject constructor(
    private val tourPlanRepository: TourPlanRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(TourPlanResultState())
    val state: StateFlow<TourPlanResultState> = _state

    private val _eventChannel = Channel<TourPlanResultEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        val args = TourPlanScreenDestination.argsFrom(savedStateHandle)
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = tourPlanRepository.getTourPlan(
                city = args.city,
                budget = args.totalBudget,
                startDateInMillis = args.startDate?.toEpochDay(),
                endDateInMillis = args.endDate?.toEpochDay(),
                totalDestination = args.totalDestination,
                category = args.category
            )

            when (result) {
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        tourPlan = result.value
                    )
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _eventChannel.send(TourPlanResultEvent.NavigateBackWithMessage(message = result.message))
                }
            }
        }
    }

    fun onDateSelected(value: Int) {
        _state.update { it.copy(selectedDate = value) }
    }
}