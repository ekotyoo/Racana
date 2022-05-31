package com.ekotyoo.racana.ui.main.tourplanlist.model

sealed class TourPlanListEvent {
    object NavigateToTourPlanResult : TourPlanListEvent()
    object DeletePlanButtonClicked : TourPlanListEvent()
}