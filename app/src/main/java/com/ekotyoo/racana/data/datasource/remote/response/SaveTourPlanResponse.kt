package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class SaveTourPlanResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
