package com.ekotyoo.racana.data.datasource.remote.api

import com.ekotyoo.racana.data.datasource.remote.request.TourPlanRequest
import com.ekotyoo.racana.data.datasource.remote.response.*
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

    @GET("tourplan/{id}")
    suspend fun getTourPlanById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<TourPlanByIdResponse>

    @GET("tourplan/active")
    suspend fun getActiveTourPlan(
        @Header("Authorization") token: String,
    ): Response<TourPlanByIdResponse>

    @POST("tourplan")
    suspend fun saveTourPlan(
        @Header("Authorization") token: String,
        @Body requestBody: TourPlanRequest,
    ): Response<BaseResponse>

    @DELETE("tourplan/{id}")
    suspend fun deleteTourPlanById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<BaseResponse>

    @FormUrlEncoded
    @PUT("tourplan/{id}")
    suspend fun updateTourPlanById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("isActive") isActive: Boolean
    ): Response<BaseResponse>

    @DELETE("tourplandate/{dateId}/destination/{destinationId}")
    suspend fun deleteTourPlanDateDestination(
        @Header("Authorization") token: String,
        @Path("dateId") dateId: Int,
        @Path("destinationId") destinationId: Int,
    ): Response<BaseResponse>

    @POST("tourplandate/{dateId}/destination/{destinationId}")
    suspend fun addTourPlanDateDestination(
        @Header("Authorization") token: String,
        @Path("dateId") dateId: Int,
        @Path("destinationId") destinationId: Int,
    ): Response<BaseResponse>

    @PUT("tourplandate/{dateId}/destination/{destinationId}/done")
    suspend fun markDestinationDone(
        @Header("Authorization") token: String,
        @Path("dateId") dateId: Int,
        @Path("destinationId") destinationId: Int,
    ): Response<BaseResponse>

    @PUT("tourplandate/{dateId}/destination/{destinationId}/notdone")
    suspend fun markDestinationNotDone(
        @Header("Authorization") token: String,
        @Path("dateId") dateId: Int,
        @Path("destinationId") destinationId: Int,
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("tourplan/{tourPlanId}/date")
    suspend fun addNewDate(
        @Header("Authorization") token: String,
        @Path("tourPlanId") tourPlanId: Int,
        @Field("date_millis") dateMillis: Long,
    ): Response<BaseResponse>
}