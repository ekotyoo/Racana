package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class TourPlanByIdResponse(

	@field:SerializedName("data")
	val data: TourPlanData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DateDestination(

	@field:SerializedName("tourplandateId")
	val tourPlanDateId: Int,

	@field:SerializedName("destinationId")
	val destinationId: Int,

	@field:SerializedName("isDone")
	val isDone: Boolean
)

data class TourPlanData(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tourplandates")
	val tourPlanDates: List<TourPlanDatesItem>,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("isActive")
	val isActive: Boolean,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
