package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@SerializedName("data")
	val data: RegisterData,

	@SerializedName("message")
	val message: String,

	@SerializedName("status")
	val status: String,
)

data class RegisterData(

	@SerializedName("name")
	val name: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("email")
	val email: String,
)
