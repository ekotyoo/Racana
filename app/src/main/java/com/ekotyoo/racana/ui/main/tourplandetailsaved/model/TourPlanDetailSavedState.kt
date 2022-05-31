package com.ekotyoo.racana.ui.main.tourplandetailsaved.model

import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.data.model.getDummyTourPlan
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultArgument

data class TourPlanDetailSavedState(
    val isLoading: Boolean = false,
    val tourPlan: TourPlan = getDummyTourPlan(),
    val selectedDate: Int = 0,
    val tourPlanResultArgs: TourPlanResultArgument? = null
) {
    val selectedDestinationList: List<TravelDestination>
        get() {
            return tourPlan.dailyList.get(selectedDate).destinationList
        }
}