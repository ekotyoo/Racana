package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@SerializedName("data")
	val data: LoginData,

	@SerializedName("message")
	val message: String,

	@SerializedName("status")
	val status: String,
)

data class LoginData(

	@SerializedName("name")
	val name: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("email")
	val email: String,

	@SerializedName("token")
	val token: String,
)
