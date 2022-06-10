package com.ekotyoo.racana.ui.main.createtourplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.ui.main.createtourplan.model.CreateTourPlanEvent
import com.ekotyoo.racana.ui.main.createtourplan.model.CreateTourPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateTourPlanViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(CreateTourPlanState())
    val state: StateFlow<CreateTourPlanState> = _state

    private val _eventChannel = Channel<CreateTourPlanEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onDateSelected(dates: List<LocalDate>) {
        if (dates.isNotEmpty()) {
            val startDate = dates.first()
            _state.update {
                it.copy(selectedStartDate = startDate)
            }
        }
    }

    fun onTotalBudgetTextFieldValueChange(value: String) {
        var total = 0
        try {
            total = value.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        _state.update { it.copy(totalBudgetTextFieldValue = total) }
    }

    fun onDestinationIncrement(value: Int) {
        if (value < 10) {
            _state.update { it.copy(totalDestinationValue = value + 1) }
        }
    }

    fun onDestinationDecrement(value: Int) {
        if (value > 0) {
            _state.update { it.copy(totalDestinationValue = value - 1) }
        }
    }

    fun onSubmitClicked() {
        submitData()
    }

    private fun submitData() {
        val stateValue = _state.value
        if (stateValue.selectedStartDate == null || stateValue.totalDestinationValue <= 0 ||
            stateValue.totalBudgetTextFieldValue < 10000
        ) {
            viewModelScope.launch {
                _eventChannel.send(CreateTourPlanEvent.SomeFieldsAreEmpty)
            }
            return
        }
        viewModelScope.launch {
            _eventChannel.send(CreateTourPlanEvent.NavigateToTourPlanResult)
        }
    }
}