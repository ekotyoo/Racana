package com.ekotyoo.racana.ui.home.tourplanlist.model

import com.ekotyoo.racana.ui.main.dashboard.model.TravelDestination
import com.ekotyoo.racana.ui.main.dashboard.model.getDummyDestination

data class TourPlanList(
    val name: String,
    val imageUrl: String,
    val date: String,
    val description: String,
    val destination: List<TravelDestination>
)

fun getDummyPlan() = List(10) {
    TourPlanList(
        "plan $it",
        "https://picsum.photos/200/300",
        "10 Mei 2022 - 22 Mei 2023",
    "description $it",
        getDummyDestination()
    )
}
