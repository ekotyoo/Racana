package com.ekotyoo.racana.ui.main.tourplandetailsaved.model

sealed class TourPlanDetailSavedEvent {
    data class NavigateBackWithMessage(val message: String) : TourPlanDetailSavedEvent()
    object NavigateToDestinationDetail : TourPlanDetailSavedEvent()
    object StartTourButtonClicked : TourPlanDetailSavedEvent()
    object DeleteDestinationButtonClicked : TourPlanDetailSavedEvent()
}