package com.ekotyoo.racana.ui.main.tourplandetailsaved.model

import android.os.Parcelable
import com.ekotyoo.racana.data.model.TourPlan
import kotlinx.parcelize.Parcelize

@Parcelize
data class TourPlanDetailSavedArgument(
    val tourPlan: TourPlan
): Parcelable