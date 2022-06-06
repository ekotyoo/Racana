package com.ekotyoo.racana.ui.main.destinationdetail.model

data class DestinationDetail(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String = "",
    val openCloseTime: String = "",
    val address: String = "",
    val ticketPrice: Long = 0,
    val ticketPriceWeekend: Long = 0,
    val isFavorite: Boolean = false,
    val description: String = "",
    val lat: Double = .0,
    val lon: Double = .0,
    val rating: Double = .0
)
