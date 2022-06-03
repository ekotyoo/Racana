package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class TourPlanResponse(

	@field:SerializedName("daily_list")
	val dailyList: List<DailyListItem>,

	@field:SerializedName("id")
	val id: Long
)

data class DailyListItem(

	@field:SerializedName("date")
	val date: Long,

	@field:SerializedName("destinations")
	val destinations: List<DestinationsItem>
)

data class DestinationsItem(

	@field:SerializedName("addresss")
	val address: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("weekendHolidayPrice")
	val weekendHolidayPrice: Int,

	@field:SerializedName("rating")
	val rating: Float,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("weekdayPrice")
	val weekdayPrice: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("categoryId")
	val categoryId: Int
)
