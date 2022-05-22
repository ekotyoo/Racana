package com.ekotyoo.racana.ui.home.dashboard.model

data class DashboardState(
    val searchTextFieldValue: String = "",
    val destinations: List<TravelDestination> = listOf()
)