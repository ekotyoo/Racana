package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class TourPlanByIdResponse(

	@SerializedName("data")
	val data: TourPlanData,

	@SerializedName("message")
	val message: String,

	@SerializedName("status")
	val status: String,
)

data class DateDestination(

	@SerializedName("isDone")
	val isDone: Boolean,

    @SerializedName("createdAt")
    val createdAt: String
)

data class TourPlanData(

	@SerializedName("createdAt")
	val createdAt: String,

	@SerializedName("description")
	val description: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("tourplandates")
	val tourPlanDates: List<TourPlanDatesDetailItem>,

	@SerializedName("title")
	val title: String,

	@SerializedName("isActive")
	val isActive: Boolean,

	@SerializedName("userId")
	val userId: Int,

	@SerializedName("updatedAt")
	val updatedAt: String,
)

data class TourPlanDatesDetailItem(

    @SerializedName("destinations")
    val destinations: List<ListDestinationsDetailItem> = emptyList(),

    @SerializedName("date_millis")
    val dateMillis: Long,

    @SerializedName("id")
    val id: Int,

    @SerializedName("tourplanId")
    val tourplanId: Int,
)

data class ListDestinationsDetailItem(

    @SerializedName("addresss")
    val address: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("rating")
    val rating: Float,

    @SerializedName("description")
    val description: String,

    @SerializedName("lon")
    val lon: Double? = null,

    @SerializedName("weekdayPrice")
    val weekdayPrice: Int = 0,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("weekendHolidayPrice")
    val weekendHolidayPrice: Int = 0,

    @SerializedName("id")
    val id: Int,

    @SerializedName("lat")
    val lat: Double? = null,

    @SerializedName("categoryId")
    val categoryId: Int? = null,

	@SerializedName("datedestination")
	val relation: DateDestination,
)
