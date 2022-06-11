package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

open class BaseResponse(

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: String? = null
)