package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ListTourPlanResponse(

	@field:SerializedName("data")
	val data: List<ListTourPlanData>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,
)

data class TourPlanDatesItem(

	@field:SerializedName("destinations")
	val destinations: List<ListDestinationsItem> = emptyList(),

	@field:SerializedName("date_millis")
	val dateMillis: Long,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tourplanId")
	val tourplanId: Int,
)

data class ListTourPlanData(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String = "",

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tourplandates")
	val tourPlanDates: List<TourPlanDatesItem> = emptyList(),

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("isActive")
	val isActive: Boolean = false,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String,
)

data class ListDestinationsItem(

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
)
