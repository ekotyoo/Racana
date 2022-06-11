package com.ekotyoo.racana.data.datasource.remote.request

import com.google.gson.annotations.SerializedName

data class PredictRequest(
	@SerializedName("totalDestination")
	val totalDestination: Int,
	@SerializedName("startDateMillis")
	val startDateMillis: Long,
	@SerializedName("budget")
	val budget: Long
)