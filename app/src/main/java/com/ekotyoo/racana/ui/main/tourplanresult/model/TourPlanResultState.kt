package com.ekotyoo.racana.ui.main.tourplanresult.model

import com.ekotyoo.racana.ui.main.dashboard.model.TravelDestination

data class TourPlanResultState(
    val tourPlan: TourPlan? = null,
    val selectedDate: Int = 0
) {
    val selectedDestinationList: List<TravelDestination>
        get() {
            return tourPlan?.dailyList?.get(selectedDate)?.destinationList ?: emptyList()
        }

}