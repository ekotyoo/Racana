package com.ekotyoo.racana.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class TravelDestination(
    val name: String,
    val imageUrl: String,
    val location: String,
    val lat: Double? = null,
    val lon: Double? = null,
    val isDone: Boolean = false,
) : Parcelable

fun getDummyDestination(): List<TravelDestination> {
    val destinationList = mutableListOf<TravelDestination>()
    repeat(5) {
        val randomNumber = Random.nextInt(0, 50)
        val randomBoolean = Random.nextBoolean()
        destinationList.add(
            TravelDestination(
                name = "Name $randomNumber",
                imageUrl = "https://picsum.photos/200/300",
                location = "Location $randomNumber",
                isDone = randomBoolean
            )
        )
    }
    return destinationList
}
