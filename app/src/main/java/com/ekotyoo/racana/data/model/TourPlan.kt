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
    val isActive: Boolean = false,
) : Parcelable {
    val imageUrl: String
        get() = dailyList.firstOrNull()?.destinationList?.firstOrNull()?.imageUrl
            ?: "https://icon-library.com/images/image-placeholder-icon/image-placeholder-icon-6.jpg"
    val period: String
        get() = dailyList.firstOrNull()?.dateFormatted + " - " + dailyList.lastOrNull()?.dateFormatted
    val totalExpense: Long
        get() = dailyList
            .map {
                it.destinationList.map { destination -> destination.weekdayPrice }
            }
            .flatten()
            .sum()
            .toLong()
}

@Parcelize
data class DailyItem(
    val id: Int = 0,
    val number: Int,
    val date: LocalDate,
    val destinationList: List<TravelDestination>,
) : Parcelable {
    val dateFormatted: String
        get() = date.format(DATE_FORMAT)
}