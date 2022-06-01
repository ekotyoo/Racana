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

	@field:SerializedName("brief")
	val brief: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Long,

	@field:SerializedName("expense")
	val expense: Int,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("long")
	val long: Double
)
