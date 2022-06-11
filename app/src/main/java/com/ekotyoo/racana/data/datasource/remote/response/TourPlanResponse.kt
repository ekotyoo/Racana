package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class TourPlanBaseResponse(
	@SerializedName("data")
	val data: TourPlanResponse
) : BaseResponse()

data class TourPlanResponse(

    @SerializedName("tourplandates")
    val dailyList: List<DailyListItem>,
)

data class DailyListItem(

	@SerializedName("date_millis")
	val dateMillis: Long,

	@SerializedName("destinations")
	val destinations: List<DestinationsItem>,
)

data class DestinationsItem(

	@SerializedName("addresss")
	val address: String,

	@SerializedName("imageUrl")
	val imageUrl: String,

	@SerializedName("name")
	val name: String,

	@SerializedName("weekendHolidayPrice")
	val weekendHolidayPrice: Int,

	@SerializedName("rating")
	val rating: Float,

	@SerializedName("description")
	val description: String,

	@SerializedName("lon")
	val lon: Double,

	@SerializedName("weekdayPrice")
	val weekdayPrice: Int,

	@SerializedName("id")
	val id: Int,

	@SerializedName("lat")
	val lat: Double,

	@SerializedName("categoryId")
	val categoryId: Int,
)
