package com.ekotyoo.racana.ui.main.tourplandetailsaved.model

sealed class TourPlanDetailSavedEvent {
    data class NavigateToDestinationDetail(val id: Int) : TourPlanDetailSavedEvent()
    object DeleteDestinationSuccess : TourPlanDetailSavedEvent()
    object DeleteDestinationError : TourPlanDetailSavedEvent()
    object MarkDestinationDoneSuccess : TourPlanDetailSavedEvent()
    object MarkDestinationNotDoneSuccess : TourPlanDetailSavedEvent()
    object MarkDestinationDoneError : TourPlanDetailSavedEvent()
    object MarkDestinationNotDoneError : TourPlanDetailSavedEvent()
    object UpdateTourPlanSuccess : TourPlanDetailSavedEvent()
    object UpdateTourPlanError : TourPlanDetailSavedEvent()
}
