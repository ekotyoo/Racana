package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ListDestinationResponse(

	@field:SerializedName("data")
	val data: List<ListDestinationData>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,
)

data class ListDestinationData(

	@field:SerializedName("addresss")
	val addresss: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("weekendHolidayPrice")
	val weekendHolidayPrice: Int,

	@field:SerializedName("rating")
	val rating: Float,

	@field:SerializedName("city")
	val city: String,

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
