package com.ekotyoo.racana.data.datasource.remote.api

import com.ekotyoo.racana.data.datasource.remote.response.BaseResponse
import com.ekotyoo.racana.data.datasource.remote.response.DestinationResponse
import com.ekotyoo.racana.data.datasource.remote.response.ListDestinationResponse
import retrofit2.Response
import retrofit2.http.*

interface DestinationApi {

    @GET("destination/top")
    suspend fun getTopDestinations(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int? = null,
    ): Response<ListDestinationResponse>

    @GET("destination")
    suspend fun searchDestination(
        @Header("Authorization") token: String,
        @Query("keyword") query: String?,
        @Query("category") category: Int?,
    ): Response<ListDestinationResponse>

    @GET("destination/{id}")
    suspend fun getDestinationById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<DestinationResponse>

    @GET("destination/favorite")
    suspend fun getFavoriteDestinations(
        @Header("Authorization") token: String
    ): Response<ListDestinationResponse>

    @POST("destination/{id}/favorite")
    suspend fun saveFavoriteDestination(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<BaseResponse>

    @DELETE("destination/{id}/favorite")
    suspend fun deleteFavoriteDestination(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<BaseResponse>
}