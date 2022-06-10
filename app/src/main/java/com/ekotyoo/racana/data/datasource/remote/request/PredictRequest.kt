package com.ekotyoo.racana.data.datasource.remote.request

data class PredictRequest(
	val totalDestination: Int,
	val startDateMillis: Long,
	val budget: Long
)

