package com.ekotyoo.racana.data.datasource.remote.request

import com.google.gson.annotations.SerializedName

data class TourPlanRequest(

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("tourplandates")
    val tourPlanDates: List<TourPlanDateRequest>
)

data class TourPlanDateRequest(

    @SerializedName("date_millis")
    val dateMillis: Long,

    @SerializedName("destinations")
    val destinations: List<Int>
)