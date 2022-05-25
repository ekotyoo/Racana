package com.ekotyoo.racana.ui.home.tourplanlist.model

sealed class TourPlanListEvent {
    object TourPlanClicked : TourPlanListEvent()
}