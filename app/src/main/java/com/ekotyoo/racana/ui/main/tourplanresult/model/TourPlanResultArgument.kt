package com.ekotyoo.racana.ui.main.tourplanresult.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class TourPlanResultArgument(
    val city: String,
    val totalBudget: Long,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val totalDestination: Int,
    val category: Int
) : Parcelable

fun getDummyTourPlanResultArgument() : TourPlanResultArgument {
    return TourPlanResultArgument(
        "Malang",
        120000,
        LocalDate.of(2022, 5, 10),
        LocalDate.of(2022, 5, 15),
        5,
        1
    )
}
