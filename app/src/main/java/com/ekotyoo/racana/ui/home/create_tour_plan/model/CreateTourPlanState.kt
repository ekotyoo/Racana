package com.ekotyoo.racana.ui.home.create_tour_plan.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CreateTourPlanState(
    val categories: List<DestinationCategory> = getCategories(),
    val cities: List<Pair<String, String>> = emptyList(),
    val citiesResult: List<Pair<String, String>> = emptyList(),
    val cityTextFieldValue: String = "",
    val totalBudgetTextFieldValue: Int = 0,
    val totalDestinationValue: Int = 0,
    val selectedCity: String = "",
    val selectedStartDate: LocalDate? = null,
    val selectedEndDate: LocalDate? = null
) {
    val startDateFormatted: String
        get() {
            if (selectedStartDate == null) return ""
            return selectedStartDate.format(DATE_FORMAT)
        }

    val endDateFormatted: String
        get() {
            if (selectedEndDate == null) return ""
            return selectedEndDate.format(DATE_FORMAT)
        }

    companion object {
        private val DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd")
    }
}