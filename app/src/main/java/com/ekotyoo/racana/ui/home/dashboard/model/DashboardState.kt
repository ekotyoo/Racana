package com.ekotyoo.racana.ui.home.dashboard.model

import com.ekotyoo.racana.data.model.UserModel

data class DashboardState(
    val user: UserModel? = null,
    val searchTextFieldValue: String = "",
    val destinations: List<TravelDestination> = listOf()
)