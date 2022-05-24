package com.ekotyoo.racana.ui.main.dashboard.model

import kotlin.random.Random

data class TravelDestination(
    val name: String,
    val imageUrl: String,
    val location: String,
    val isDone: Boolean = false,
)

fun getDummyDestination(): List<TravelDestination> {
    val destinationList = mutableListOf<TravelDestination>()
    repeat(5) {
        val randomNumber = Random.nextInt(0, 50)
        val randomBoolean = Random.nextBoolean()
        destinationList.add(
            TravelDestination(
                "Name $randomNumber",
                "https://picsum.photos/200/300",
                "Location $randomNumber",
                randomBoolean
            )
        )
    }
    return destinationList
}
