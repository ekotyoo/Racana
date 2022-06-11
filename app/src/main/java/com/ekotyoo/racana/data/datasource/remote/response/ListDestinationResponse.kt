package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ListDestinationResponse(

	@SerializedName("data")
	val data: List<ListDestinationData>,

	@SerializedName("message")
	val message: String,

	@SerializedName("status")
	val status: String,
)

data class ListDestinationData(

	@SerializedName("addresss")
	val addresss: String,

	@SerializedName("imageUrl")
	val imageUrl: String,

	@SerializedName("name")
	val name: String,

	@SerializedName("weekendHolidayPrice")
	val weekendHolidayPrice: Int,

	@SerializedName("rating")
	val rating: Float,

	@SerializedName("city")
	val city: String,

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
	val categoryId: Int
)
