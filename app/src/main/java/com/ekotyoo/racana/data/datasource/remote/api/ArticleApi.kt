package com.ekotyoo.racana.data.datasource.remote.api

import com.ekotyoo.racana.data.datasource.remote.response.ArticleDetailResponse
import com.ekotyoo.racana.data.datasource.remote.response.ListArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ArticleApi {
    @GET("article")
    suspend fun getAllArticles(@Header("Authorization") token: String): Response<ListArticleResponse>

    @GET("article/dashboard")
    suspend fun getDashboardArticles(@Header("Authorization") token: String): Response<ListArticleResponse>

    @GET("article/{id}")
    suspend fun getArticleDetailById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<ArticleDetailResponse>
}