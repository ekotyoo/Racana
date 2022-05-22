package com.ekotyoo.racana.ui.home.create_tour_plan.model

data class CreateTourPlanState(
    val categories: List<DestinationCategory> = getCategories(),
    val cities: List<Pair<String, String>> = emptyList(),
    val citiesResult: List<Pair<String, String>> = emptyList(),
    val cityTextFieldValue: String = "",
    val totalBudgetTextFieldValue: Int = 0,
    val totalDestinationValue: Int = 0,
    val selectedCity: String = ""
)