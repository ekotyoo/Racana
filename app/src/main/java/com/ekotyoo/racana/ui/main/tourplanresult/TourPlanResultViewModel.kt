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
import timber.log.Timber
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class TourPlanResultViewModel @Inject constructor(
    private val tourPlanRepository: TourPlanRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(TourPlanResultState())
    val state: StateFlow<TourPlanResultState> = _state

    private val _eventChannel = Channel<TourPlanResultEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        val args = TourPlanScreenDestination.argsFrom(savedStateHandle)
        _state.update { it.copy(tourPlanResultArgs = args) }
        viewModelScope.launch {
            getTourPlan(
                budget = args.totalBudget,
                startDateInMillis = args.startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
                    .toEpochMilli(),
                totalDestination = args.totalDestination,
            )
        }
    }

    private suspend fun getTourPlan(
        budget: Long,
        startDateInMillis: Long,
        totalDestination: Int,
    ) {
        _state.update { it.copy(isLoading = true) }
        val result = tourPlanRepository.getTourPlan(
            budget = budget,
            startDateInMillis = startDateInMillis,
            totalDestination = totalDestination,
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
        _state.update { it.copy(isLoading = false) }
    }

    fun onTitleTextFieldValueChange(value: String) {
        _state.update { it.copy(titleTextFieldValue = value) }
        val errorMessage = if (value.isNotEmpty()) {
            when {
                value.length > 30 -> OVER_30_CHARS
                else -> null
            }
        } else null
        _state.update { it.copy(titleTextFieldErrorValue = errorMessage) }
    }

    fun onDescriptionTextFieldValueChange(value: String) {
        _state.update { it.copy(descriptionTextFieldValue = value) }
        val errorMessage = if (value.isNotEmpty()) {
            when {
                value.length > 200 -> OVER_200_CHARS
                else -> null
            }
        } else null
        _state.update { it.copy(descriptionTextFieldErrorValue = errorMessage) }
    }

    fun onDateSelected(value: Int) {
        _state.update { it.copy(selectedDate = value) }
    }

    fun onAddDestinationClick() {
        when (_state.value.predictCounter) {
            0 -> predictDestination(0)
            1 -> predictDestination(1)
        }
    }

    fun onSaveTourPlanSubmitted() {
        saveTourPlan()
    }

    fun navigateToDestinationDetail(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(TourPlanResultEvent.NavigateToDestinationDetail(id))
        }
    }

    private fun predictDestination(flag: Int) {
        _state.value.tourPlan?.dailyList?.first()?.destinationList?.last()?.name?.let {
            viewModelScope.launch {
                when (val result =
                    tourPlanRepository.predictDestination(flag, destinationName = it)) {
                    is Result.Success -> {
                        val newDestination = result.value
                        val tourPlan = _state.value.tourPlan
                        if (tourPlan != null) {
                            tourPlan.dailyList[_state.value.selectedDate].destinationList.add(
                                newDestination)
                            _state.update { it.copy(predictCounter = _state.value.predictCounter + 1) }
                        }
                    }
                    is Result.Error -> {
                        _eventChannel.send(TourPlanResultEvent.PredictDestinationError)
                    }
                }
            }
        }
    }

    private fun saveTourPlan() {
        val tourPlan = _state.value.tourPlan
        if (tourPlan != null) {
            viewModelScope.launch {
                when (tourPlanRepository.saveTourPlan(tourPlan,
                    _state.value.titleTextFieldValue,
                    _state.value.descriptionTextFieldValue)) {
                    is Result.Error -> {
                        _eventChannel.send(TourPlanResultEvent.SaveTourPlanError)
                        Timber.d("Gagal menyimpan tour plan.")
                    }
                    is Result.Success -> {
                        _eventChannel.send(TourPlanResultEvent.SaveTourPlanSuccess)
                        Timber.d("Berhasil menyimpan tour plan")
                    }
                }
            }
        }
    }

    companion object {
        const val OVER_200_CHARS = "over_200_chars"
        const val OVER_30_CHARS = "over_30_chars"
    }
}