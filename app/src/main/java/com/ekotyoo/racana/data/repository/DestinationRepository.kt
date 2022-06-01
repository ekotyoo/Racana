package com.ekotyoo.racana.data.repository

import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.datasource.local.UserPreferencesDataStore
import com.ekotyoo.racana.data.datasource.remote.api.DestinationApi
import com.ekotyoo.racana.data.model.TravelDestination
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class DestinationRepository @Inject constructor(
    private val destinationApi: DestinationApi,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) {
    suspend fun getDestinations(): Result<List<TravelDestination>> {
        try {
            val token = userPreferencesDataStore.userData.first().token
            val response = destinationApi.getDestination(token ?: "")
            val data = response.body()?.data
            return if (response.isSuccessful && data != null) {
                val destinations = data.map {
                    TravelDestination(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.imageUrl,
                        location = it.location,
                        lat = it.lat,
                        lon = it.lon,
                        expense = it.expense.toLong()
                    )
                }
                Result.Success(destinations)
            } else {
                Result.Error("Failed getting data.", null)
            }
        } catch (e: IOException) {
            Timber.d(e)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d(e)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }
}