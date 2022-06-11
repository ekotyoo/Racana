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
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class TourPlanDetailSavedViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tourPlanRepository: TourPlanRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TourPlanDetailSavedState())
    val state: StateFlow<TourPlanDetailSavedState> = _state

    private val _eventChannel =
        Channel<TourPlanDetailSavedEvent>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        val args = TourPlanDetailSavedScreenDestination.argsFrom(savedStateHandle)
        val tourPlan = args.tourPlan
        tourPlan.id?.let {
            getTourPlanById(tourPlan.id.toInt())
        }
    }

    fun onDateSelected(value: Int) {
        _state.update { it.copy(selectedDate = value) }
    }

    fun onDeleteDateClicked(id: Int) {
        deleteDate(id)
    }

    fun navigateToDestinationDetail(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(TourPlanDetailSavedEvent.NavigateToDestinationDetail(id))
        }
    }

    fun onDestinationDeleteButtonClicked(destinationId: Int) {
        deleteTourPlanDateDestination(destinationId)
    }

    fun onDestinationToggleDoneClicked(destinationId: Int) {
        val destination = _state.value.tourPlan
            .dailyList[_state.value.selectedDate]
            .destinationList
            .find { it.id == destinationId }

        destination?.let {
            if (destination.isDone) {
                markDestinationNotDone(destinationId)
            } else {
                markDestinationDone(destinationId)
            }
        }
    }

    fun onToggleActive() {
        _state.value.tourPlan.id?.let { updateTourPlan(it.toInt()) }
    }

    fun onSearchResultClick(destinationId: Int) {
        addDestination(destinationId)
    }

    fun onDateSelected(dates: List<LocalDate>) {
        viewModelScope.launch { _eventChannel.send(TourPlanDetailSavedEvent.DismissDateDialog) }
        if (dates.isNotEmpty()) {
            val date = dates.first()
            val dateMillis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            addNewDate(dateMillis)
        }
    }

    private fun deleteDate(dateId: Int) {
        viewModelScope.launch {
            val tourPlanId = _state.value.tourPlan.id
            tourPlanId?.let {
                when (tourPlanRepository.deleteDate(tourPlanId.toInt(), dateId)) {
                    is Result.Success -> {
                        if (_state.value.selectedDate > 0) {
                            _state.update { it.copy(selectedDate = _state.value.selectedDate - 1) }
                        }
                        val id = _state.value.tourPlan.id?.toInt()
                        id?.let {
                            getTourPlanById(id)
                        }
                        _eventChannel.send(TourPlanDetailSavedEvent.DeleteDateSuccess)
                    }
                    is Result.Error -> {
                        _eventChannel.send(TourPlanDetailSavedEvent.DeleteDateError)
                    }
                }
            }
        }
    }

    private fun addNewDate(dateMillis: Long) {
        viewModelScope.launch {
            val tourPlanId = _state.value.tourPlan.id
            tourPlanId?.let {
                when (tourPlanRepository.addNewDate(tourPlanId.toInt(), dateMillis)) {
                    is Result.Success -> {
                        val id = _state.value.tourPlan.id?.toInt()
                        id?.let {
                            getTourPlanById(id)
                        }
                        _eventChannel.send(TourPlanDetailSavedEvent.AddDateSuccess)
                    }
                    is Result.Error -> {
                        _eventChannel.send(TourPlanDetailSavedEvent.AddDateError)
                    }
                }
            }
        }
    }

    private fun addDestination(destinationId: Int) {
        viewModelScope.launch {
            val dateId = _state.value.tourPlan.dailyList[_state.value.selectedDate].id
            when (tourPlanRepository.addTourPlanDateDestination(dateId, destinationId)) {
                is Result.Success -> {
                    val id = _state.value.tourPlan.id?.toInt()
                    id?.let {
                        getTourPlanById(id)
                    }
                    _eventChannel.send(TourPlanDetailSavedEvent.AddDestinationSuccess)
                }
                is Result.Error -> {
                    _eventChannel.send(TourPlanDetailSavedEvent.AddDestinationError)
                }
            }
            _eventChannel.send(TourPlanDetailSavedEvent.CloseSearchSheet)
        }
    }

    private fun updateTourPlan(id: Int) {
        val newIsActive = !_state.value.tourPlan.isActive
        viewModelScope.launch {
            when (tourPlanRepository.updateTourPlan(id, newIsActive)) {
                is Result.Success -> {
                    _state.value.tourPlan.id?.toInt()?.let {
                        getTourPlanById(it)
                    }
                    _eventChannel.send(TourPlanDetailSavedEvent.UpdateTourPlanSuccess)
                }
                is Result.Error -> {
                    _eventChannel.send(TourPlanDetailSavedEvent.UpdateTourPlanError)
                }
            }
        }
    }

    private fun markDestinationDone(destinationId: Int) {
        viewModelScope.launch {
            val dateId = _state.value.tourPlan.dailyList[_state.value.selectedDate].id
            when (tourPlanRepository.markDestinationDone(dateId, destinationId)) {
                is Result.Success -> {
                    val id = _state.value.tourPlan.id?.toInt()
                    id?.let {
                        getTourPlanById(id)
                    }
                    _eventChannel.send(TourPlanDetailSavedEvent.MarkDestinationDoneSuccess)
                }
                is Result.Error -> {
                    _eventChannel.send(TourPlanDetailSavedEvent.MarkDestinationDoneError)
                }
            }
        }
    }


    private fun markDestinationNotDone(destinationId: Int) {
        viewModelScope.launch {
            val dateId = _state.value.tourPlan.dailyList[_state.value.selectedDate].id
            when (tourPlanRepository.markDestinationNotDone(dateId, destinationId)) {
                is Result.Success -> {
                    val id = _state.value.tourPlan.id?.toInt()
                    id?.let {
                        getTourPlanById(id)
                    }
                    _eventChannel.send(TourPlanDetailSavedEvent.MarkDestinationNotDoneSuccess)
                }
                is Result.Error -> {
                    _eventChannel.send(TourPlanDetailSavedEvent.MarkDestinationNotDoneError)
                }
            }
        }
    }

    private fun deleteTourPlanDateDestination(destinationId: Int) {
        viewModelScope.launch {
            val dateId = _state.value.tourPlan.dailyList[_state.value.selectedDate].id
            when (tourPlanRepository.deleteTourPlanDateDestination(dateId, destinationId)) {
                is Result.Success -> {
                    val id = _state.value.tourPlan.id?.toInt()
                    id?.let {
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
            when (val result = tourPlanRepository.getSavedTourPlanById(id)) {
                is Result.Success -> {
                    _state.update { it.copy(tourPlan = result.value) }
                }
                is Result.Error -> {
                    _eventChannel.send(TourPlanDetailSavedEvent.GetTourPlanDetailError)
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}