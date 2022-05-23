package com.ekotyoo.racana.ui.home.create_tour_plan

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.AssetLoader
import com.ekotyoo.racana.ui.home.create_tour_plan.model.CreateTourPlanEvent
import com.ekotyoo.racana.ui.home.create_tour_plan.model.CreateTourPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateTourPlanViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(CreateTourPlanState())
    val state: StateFlow<CreateTourPlanState> = _state

    private val _eventChannel = Channel<CreateTourPlanEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.update { it.copy(cities = AssetLoader.getCityProvince(context)) }
            } catch (e: IOException) {
                Timber.d(e.message)
            }
        }
    }

    fun onDateSelected(dates: List<LocalDate>) {
        if (dates.isNotEmpty()) {
            val startDate = dates.first()
            val endDate = dates.last()
            _state.update {
                it.copy(selectedStartDate = startDate, selectedEndDate = endDate)
            }
        }
    }

    fun onCitiesTextFieldValueChange(value: String) {
        _state.update { it.copy(cityTextFieldValue = value) }
        searchCity(value)
    }

    fun onCityTextFieldCleared() {
        _state.update { it.copy(cityTextFieldValue = "") }
    }

    fun onCitySelected(city: String) {
        _state.update { it.copy(selectedCity = city, citiesResult = emptyList()) }
        onCityTextFieldCleared()
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
        _state.update { it.copy(totalDestinationValue = value + 1) }
    }

    fun onDestinationDecrement(value: Int) {
        _state.update { it.copy(totalDestinationValue = value - 1) }
    }

    private fun searchCity(query: String) {
        val result = _state.value.cities.filter {
            it.first.contains(query, ignoreCase = true)
        }
        _state.update { it.copy(citiesResult = result) }
    }
}