package com.ekotyoo.racana.ui.main.tourplanlist.model

sealed class TourPlanListEvent {
    object NavigateToTourPlanDetail : TourPlanListEvent()
    object DeletePlanButtonClicked : TourPlanListEvent()
}