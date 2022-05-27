package com.ekotyoo.racana.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
data class TourPlan(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val dailyList: List<DailyItem>
) : Parcelable

@Parcelize
data class DailyItem(
    val number: Int,
    val date: LocalDate,
    val destinationList: List<TravelDestination>
) : Parcelable {
    val dateFormatted: String
        get() {
            return date.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
        }
}

fun getDummyTourPlan(): TourPlan {
    val dailyItemList: MutableList<DailyItem> = mutableListOf()
    repeat(7) {
        dailyItemList.add(
            DailyItem(
                number = it + 1,
                date = LocalDate.now().plusDays(it.toLong()),
                destinationList = getDummyDestination()
            )
        )
    }

    return TourPlan(title = "Tour Plan Title", dailyList = dailyItemList)
}