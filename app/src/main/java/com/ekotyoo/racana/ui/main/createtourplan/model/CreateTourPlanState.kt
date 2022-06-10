package com.ekotyoo.racana.ui.main.createtourplan.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CreateTourPlanState(
    val isLoading: Boolean = false,
    val totalBudgetTextFieldValue: Int = 0,
    val totalDestinationValue: Int = 0,
    val selectedStartDate: LocalDate? = null,
) {
    val startDateFormatted: String
        get() {
            if (selectedStartDate == null) return ""
            return selectedStartDate.format(DATE_FORMAT)
        }
}

val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")