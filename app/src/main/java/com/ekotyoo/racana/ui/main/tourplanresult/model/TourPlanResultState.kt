package com.ekotyoo.racana.ui.main.tourplanresult.model

import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.model.TravelDestination

data class TourPlanResultState(
    val isLoading: Boolean = false,
    val tourPlan: TourPlan? = null,
    val selectedDate: Int = 0,
    val tourPlanResultArgs: TourPlanResultArgument? = null
) {
    val selectedDestinationList: List<TravelDestination>
        get() {
            return tourPlan?.dailyList?.get(selectedDate)?.destinationList ?: emptyList()
        }
}