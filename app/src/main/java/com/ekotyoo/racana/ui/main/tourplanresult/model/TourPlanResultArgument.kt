package com.ekotyoo.racana.ui.main.tourplanresult.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class TourPlanResultArgument(
    val totalBudget: Long,
    val startDate: LocalDate,
    val totalDestination: Int,
) : Parcelable
