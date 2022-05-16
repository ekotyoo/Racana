package com.ekotyoo.racana.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("token")
    val token: String
)

data class User(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String
)
