package com.ekotyoo.racana.data.datasource.remote

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TourPlanApi {

    @FormUrlEncoded
    @POST("tourplan")
    suspend fun getTourPlan(
        @Field("city") city: String,
        @Field("budget") budget: Long,
        @Field("start_date") startDateInMillis: Long?,
        @Field("end_date") endDateInMillis: Long?,
        @Field("total_destination") totalDestination: Int,
        @Field("category_id") category: Int
    ): Response<TourPlanResponse>
}