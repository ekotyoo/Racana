package com.ekotyoo.racana.di

import com.ekotyoo.racana.data.datasource.remote.api.ArticleApi
import com.ekotyoo.racana.data.datasource.remote.api.AuthApi
import com.ekotyoo.racana.data.datasource.remote.api.DestinationApi
import com.ekotyoo.racana.data.datasource.remote.api.TourPlanApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideTourPlanApi(retrofit: Retrofit): TourPlanApi =
        retrofit.create(TourPlanApi::class.java)

    @Singleton
    @Provides
    fun provideDestinationApi(retrofit: Retrofit): DestinationApi =
        retrofit.create(DestinationApi::class.java)

    @Singleton
    @Provides
    fun provideArticleApi(retrofit: Retrofit): ArticleApi =
        retrofit.create(ArticleApi::class.java)
}

private const val BASE_URL = "https://racana-api-mg3l2wnsyq-et.a.run.app/api/"