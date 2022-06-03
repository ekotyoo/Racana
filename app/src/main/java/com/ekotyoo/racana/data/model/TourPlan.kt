package com.ekotyoo.racana.data.model

import android.os.Parcelable
import com.ekotyoo.racana.ui.main.createtourplan.model.DATE_FORMAT
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class TourPlan(
    val id: Long? = null,
    val title: String? = null,
    val description: String? = null,
    val dailyList: List<DailyItem>,
) : Parcelable {
    val imageUrl: String
        get() = dailyList.first().destinationList.first().imageUrl
    val period: String
        get() = dailyList.first().dateFormatted + " - " + dailyList.last().dateFormatted
}

@Parcelize
data class DailyItem(
    val number: Int,
    val date: LocalDate,
    val destinationList: List<TravelDestination>,
) : Parcelable {
    val dateFormatted: String
        get() = date.format(DATE_FORMAT)
}