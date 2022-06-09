package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class TourPlanByIdResponse(

	@field:SerializedName("data")
	val data: TourPlanData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,
)

data class DateDestination(

	@field:SerializedName("isDone")
	val isDone: Boolean,

    @field:SerializedName("createdAt")
    val createdAt: String
)

data class TourPlanData(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tourplandates")
	val tourPlanDates: List<TourPlanDatesDetailItem>,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("isActive")
	val isActive: Boolean,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String,
)

data class TourPlanDatesDetailItem(

    @field:SerializedName("destinations")
    val destinations: List<ListDestinationsDetailItem> = emptyList(),

    @field:SerializedName("date_millis")
    val dateMillis: Long,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("tourplanId")
    val tourplanId: Int,
)

data class ListDestinationsDetailItem(

    @field:SerializedName("addresss")
    val address: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("rating")
    val rating: Float,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("weekdayPrice")
    val weekdayPrice: Int = 0,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("weekendHolidayPrice")
    val weekendHolidayPrice: Int = 0,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("lat")
    val lat: Double? = null,

    @field:SerializedName("categoryId")
    val categoryId: Int? = null,

	@field:SerializedName("datedestination")
	val relation: DateDestination,
)
