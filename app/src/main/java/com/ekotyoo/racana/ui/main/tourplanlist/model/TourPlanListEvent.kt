package com.ekotyoo.racana.ui.main.tourplanlist.model

import com.ekotyoo.racana.data.model.TourPlan

sealed class TourPlanListEvent {
    data class NavigateToTourPlanDetail(val tourPlan: TourPlan) : TourPlanListEvent()
    object DeleteTourPlanSuccess : TourPlanListEvent()
    object DeleteTourPlanFailed : TourPlanListEvent()
}