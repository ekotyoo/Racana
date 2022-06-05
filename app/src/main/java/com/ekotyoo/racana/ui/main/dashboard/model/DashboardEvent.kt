package com.ekotyoo.racana.ui.main.dashboard.model

sealed class DashboardEvent {
    data class NavigateToDetailDestination(val id: Int): DashboardEvent()
    object allDestinationClicked: DashboardEvent()
}