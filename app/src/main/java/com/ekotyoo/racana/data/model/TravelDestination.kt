package com.ekotyoo.racana.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelDestination(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val city: String = "",
    val address: String = "",
    val rating: Float = 0f,
    val imageUrl: String = "",
    val weekdayPrice: Int = 0,
    val weekendHolidayPrice: Int = 0,
    val lon: Double = .0,
    val lat: Double = .0,
    val categoryId: Int = 0,
    val isDone: Boolean = false
) : Parcelable
