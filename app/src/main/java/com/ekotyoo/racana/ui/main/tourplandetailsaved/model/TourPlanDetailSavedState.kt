package com.ekotyoo.racana.ui.main.tourplandetailsaved.model

import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.model.TravelDestination

data class TourPlanDetailSavedState(
    val isLoading: Boolean = false,
    val tourPlan: TourPlan = TourPlan(0, "", "", emptyList()),
    val selectedDate: Int = 0,
) {
    val selectedDestinationList: List<TravelDestination>
        get() {
            return tourPlan.dailyList[selectedDate].destinationList
        }
}