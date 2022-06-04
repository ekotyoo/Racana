package com.ekotyoo.racana.data.datasource.remote.api

import com.ekotyoo.racana.data.datasource.remote.request.TourPlanRequest
import com.ekotyoo.racana.data.datasource.remote.response.ListTourPlanResponse
import com.ekotyoo.racana.data.datasource.remote.response.SaveTourPlanResponse
import com.ekotyoo.racana.data.datasource.remote.response.TourPlanResponse
import retrofit2.Response
import retrofit2.http.*

interface TourPlanApi {

    @FormUrlEncoded
    @POST("predict")
    suspend fun getTourPlanPrediction(
        @Header("Authorization") token: String,
        @Field("city") city: String,
        @Field("budget") budget: Long,
        @Field("start_date") startDateInMillis: Long?,
        @Field("end_date") endDateInMillis: Long?,
        @Field("total_destination") totalDestination: Int,
        @Field("category_id") category: Int,
    ): Response<TourPlanResponse>

    @GET("tourplan")
    suspend fun getAllTourPlan(
        @Header("Authorization") token: String,
    ): Response<ListTourPlanResponse>

    @POST("tourplan")
    suspend fun saveTourPlan(
        @Header("Authorization") token: String,
        @Body requestBody: TourPlanRequest,
    ): Response<SaveTourPlanResponse>
}