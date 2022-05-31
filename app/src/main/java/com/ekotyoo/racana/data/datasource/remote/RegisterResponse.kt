package com.ekotyoo.racana.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: RegisterData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,
)

data class RegisterData(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,
)
