package com.ekotyoo.racana.data.datasource.remote.api

import com.ekotyoo.racana.data.datasource.remote.response.DestinationResponse
import com.ekotyoo.racana.data.datasource.remote.response.ListDestinationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DestinationApi {

    @GET("destination")
    suspend fun getDestination(
        @Header("Authorization") token: String,
    ): Response<ListDestinationResponse>

    @GET("destination/top")
    suspend fun getTopDestinations(
        @Header("Authorization") token: String,
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
}