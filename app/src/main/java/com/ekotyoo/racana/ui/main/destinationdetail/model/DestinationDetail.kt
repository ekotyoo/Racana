package com.ekotyoo.racana.ui.main.destinationdetail.model

data class DestinationDetail(
    val name: String,
    val imageUrl: String,
    val openCloseTime: String,
    val address: String,
    val ticketPrice: String,
    val isFavorite: Boolean = false,
    val description: String,
    val lat: Double,
    val lon: Double
)

fun getDummyDetailDestination(): DestinationDetail {
    return DestinationDetail(
        name = "Pantai Bagus",
        imageUrl = "https://picsum.photos/200/300",
        openCloseTime = "08.00-22.00",
        address = "Jln. Tepi Pantai",
        ticketPrice = "10000000",
        description = "Pantainya sangat bagus",
        lat = 0.0,
        lon = 0.0
    )
}