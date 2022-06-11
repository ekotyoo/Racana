package com.ekotyoo.racana.ui.main.tourplandetailsaved.model

sealed class TourPlanDetailSavedEvent {
    data class NavigateToDestinationDetail(val id: Int) : TourPlanDetailSavedEvent()
    object GetTourPlanDetailError : TourPlanDetailSavedEvent()
    object DeleteDestinationSuccess : TourPlanDetailSavedEvent()
    object DeleteDestinationError : TourPlanDetailSavedEvent()
    object AddDestinationSuccess : TourPlanDetailSavedEvent()
    object AddDestinationError : TourPlanDetailSavedEvent()
    object MarkDestinationDoneSuccess : TourPlanDetailSavedEvent()
    object MarkDestinationNotDoneSuccess : TourPlanDetailSavedEvent()
    object MarkDestinationDoneError : TourPlanDetailSavedEvent()
    object MarkDestinationNotDoneError : TourPlanDetailSavedEvent()
    object UpdateTourPlanSuccess : TourPlanDetailSavedEvent()
    object UpdateTourPlanError : TourPlanDetailSavedEvent()
    object AddDateError : TourPlanDetailSavedEvent()
    object AddDateSuccess : TourPlanDetailSavedEvent()
    object DeleteDateSuccess : TourPlanDetailSavedEvent()
    object DeleteDateError : TourPlanDetailSavedEvent()
}
