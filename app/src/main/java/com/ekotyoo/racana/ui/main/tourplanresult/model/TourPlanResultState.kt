package com.ekotyoo.racana.ui.main.tourplanresult.model

import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.model.TravelDestination

data class TourPlanResultState(
    val isLoading: Boolean = false,
    val tourPlan: TourPlan? = null,
    val selectedDate: Int = 0,
    var predictCounter: Int = 0,
    val tourPlanResultArgs: TourPlanResultArgument? = null,
    val titleTextFieldValue: String = "",
    val titleTextFieldErrorValue: String? = null,
    val descriptionTextFieldValue: String = "",
    val descriptionTextFieldErrorValue: String? = null
) {
    val selectedDestinationList: List<TravelDestination>
        get() {
            return tourPlan?.dailyList?.get(selectedDate)?.destinationList ?: emptyList()
        }

    val submitButtonEnabled: Boolean
        get() = titleTextFieldErrorValue.isNullOrEmpty()  && descriptionTextFieldErrorValue.isNullOrEmpty()
                && titleTextFieldValue.isNotBlank() && descriptionTextFieldValue.isNotBlank()
}