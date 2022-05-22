package com.ekotyoo.racana.ui.home.dashboard.model

data class TravelDestination(
    val name: String,
    val imageUrl: String,
    val location: String
)

fun getDummyDestination() =
    List(10) { TravelDestination("Name $it", "https://picsum.photos/200/300", "Location $it") }