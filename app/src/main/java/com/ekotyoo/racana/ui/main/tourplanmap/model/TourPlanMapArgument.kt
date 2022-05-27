package com.ekotyoo.racana.ui.main.tourplanmap.model

import android.os.Parcelable
import com.ekotyoo.racana.data.model.TourPlan
import kotlinx.parcelize.Parcelize

@Parcelize
data class TourPlanMapArgument(
    val tourPlan: TourPlan?
) : Parcelable