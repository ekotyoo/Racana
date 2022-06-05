package com.ekotyoo.racana.ui.main.destinationdetail.model

data class DestinationDetail(
    val name: String = "",
    val imageUrl: String = "",
    val openCloseTime: String = "",
    val address: String = "",
    val ticketPrice: Long = 0,
    val ticketPriceWeekend: Long = 0,
    val isFavorite: Boolean = false,
    val description: String = "",
    val lat: Double = .0,
    val lon: Double = .0
)

fun getDummyDetailDestination(): DestinationDetail {
    return DestinationDetail(
        name = "Pantai Bagus",
        imageUrl = "https://picsum.photos/200/300",
        openCloseTime = "08.00-22.00",
        address = "Jln. Tepi Pantai",
        ticketPrice = 10000000,
        description = "Pantainya sangat bagus",
        lat = 1.35,
        lon = 103.87
    )
}