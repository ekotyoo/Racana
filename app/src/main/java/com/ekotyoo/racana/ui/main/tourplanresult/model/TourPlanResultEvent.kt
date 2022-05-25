package com.ekotyoo.racana.ui.main.tourplanresult.model

sealed class TourPlanResultEvent {
    data class NavigateBackWithMessage(val message: String) : TourPlanResultEvent()
}