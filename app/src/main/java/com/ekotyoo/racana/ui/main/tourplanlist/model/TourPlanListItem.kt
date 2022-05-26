package com.ekotyoo.racana.ui.main.tourplanlist.model

import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.data.model.getDummyDestination

data class TourPlanListItem(
    val name: String,
    val imageUrl: String,
    val date: String,
    val description: String,
    val destination: List<TravelDestination>,
)

fun getDummyPlan() = List(10) {
    TourPlanListItem(
        "plan $it",
        "https://picsum.photos/200/300",
        "10 Mei 2022 - 22 Mei 2023",
        "description $it",
        getDummyDestination()
    )
}
