package com.ekotyoo.racana.ui.main.tourplanresult.model

import com.ekotyoo.racana.ui.main.dashboard.model.TravelDestination
import com.ekotyoo.racana.ui.main.dashboard.model.getDummyDestination
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class TourPlan(
    val title: String? = null,
    val dailyList: List<DailyItem>
)

data class DailyItem(
    val number: Int,
    val date: LocalDate,
    val destinationList: List<TravelDestination>
) {
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

    return TourPlan("Tour Plan Title", dailyItemList)
}