package com.ekotyoo.racana.data.datasource.remote.api

import com.ekotyoo.racana.data.datasource.remote.response.ListDestinationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface DestinationApi {

    @GET("destination")
    suspend fun getDestination(
        @Header("Authorization") token: String,
    ): Response<ListDestinationResponse>
}