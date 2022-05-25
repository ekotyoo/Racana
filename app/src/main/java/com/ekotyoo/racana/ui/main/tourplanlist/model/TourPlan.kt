package com.ekotyoo.racana.ui.home.tour_plan_list.model

import com.ekotyoo.racana.ui.home.dashboard.model.TravelDestination
import com.ekotyoo.racana.ui.home.dashboard.model.getDummyDestination

data class TourPlan(
    val name: String,
    val imageUrl: String,
    val date: String,
    val description: String,
    val destination: List<TravelDestination>
)

fun getDummyPlan() = List(10) {
    TourPlan(
        "plan $it",
        "https://picsum.photos/200/300",
        "10 Mei 2022 - 22 Mei 2023",
    "description $it",
    getDummyDestination())
}
