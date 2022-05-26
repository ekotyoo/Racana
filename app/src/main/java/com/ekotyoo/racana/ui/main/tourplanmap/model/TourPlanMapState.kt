package com.ekotyoo.racana.ui.main.tourplanmap.model

import com.ekotyoo.racana.ui.main.dashboard.model.TravelDestination
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlan

data class TourPlanMapState(
    val tourPlan: TourPlan? = null,
    val selectedDate: Int = 0
) {
    val selectedDestinationList: List<TravelDestination>
        get() {
            return tourPlan?.dailyList?.get(selectedDate)?.destinationList ?: emptyList()
        }
}