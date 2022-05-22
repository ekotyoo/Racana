package com.ekotyoo.racana.ui.home.create_tour_plan

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.AssetLoader
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
import javax.inject.Inject

@HiltViewModel
class CreateTourPlanViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(CreateTourPlanState())
    val state: StateFlow<CreateTourPlanState> = _state

    private val _eventChannel = Channel<CreateTourPlanScreenEvent>()
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

data class CreateTourPlanState(
    val categories: List<DestinationCategory> = getCategories(),
    val cities: List<Pair<String, String>> = emptyList(),
    val citiesResult: List<Pair<String, String>> = emptyList(),
    val cityTextFieldValue: String = "",
    val totalBudgetTextFieldValue: Int = 0,
    val totalDestinationValue: Int = 0,
    val selectedCity: String = ""
)

sealed class CreateTourPlanScreenEvent {
    data class Test(val message: String) : CreateTourPlanScreenEvent()
}

data class DestinationCategory(
    val id: String,
    val title: String,
    val imageUrl: String,
)

fun getCategories(): List<DestinationCategory> {
    return listOf(
        DestinationCategory(
            "1",
            "Pantai",
            "https://cdn.pixabay.com/photo/2014/08/15/11/29/beach-418742_960_720.jpg"
        ),
        DestinationCategory(
            "2",
            "Lembah",
            "https://cdn.pixabay.com/photo/2019/07/14/10/48/vineyards-4336787_960_720.jpg"
        ),
        DestinationCategory(
            "3",
            "Gunung",
            "https://cdn.pixabay.com/photo/2021/08/08/20/37/mountains-6531903_960_720.jpg"
        ),
        DestinationCategory(
            "4",
            "Hutan",
            "https://cdn.pixabay.com/photo/2019/02/17/22/50/jungle-4003374_960_720.jpg"
        )
    )
}