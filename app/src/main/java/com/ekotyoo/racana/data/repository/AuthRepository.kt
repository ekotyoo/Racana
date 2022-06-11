package com.ekotyoo.racana.data.repository

import com.ekotyoo.racana.data.datasource.local.UserPreferencesDataStore
import com.ekotyoo.racana.data.datasource.remote.api.AuthApi
import com.ekotyoo.racana.data.model.UserModel
import dagger.hilt.android.scopes.ViewModelScoped
import com.ekotyoo.racana.core.utils.Result
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val userPreferencesDataStore: UserPreferencesDataStore
) {
    val userData = userPreferencesDataStore.userData

    suspend fun login(email: String, password: String): Result<UserModel> {
        try {
            val response = authApi.login(email, password)
            val data = response.body()?.data
            return if (response.isSuccessful && data != null) {
                val user = UserModel(
                    data.id,
                    data.name,
                    data.email,
                    data.token
                )
                userPreferencesDataStore.saveUserData(user)
                Result.Success(user)
            } else if(response.code() == 401) {
                Result.Error("Email atau Password harus sesuai.", null)
            } else {
                Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
            }
        } catch (e: IOException) {
            Timber.d("IOException: " + e.message)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d("HttpException: " + e.message)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }

    suspend fun register(name: String, email: String, password: String): Result<UserModel> {
        try {
            val response = authApi.register(name, email, password)
            val data = response.body()?.data
            return if (response.isSuccessful && data != null) {
                val user = UserModel(
                    data.id,
                    data.name,
                    data.email,
                    ""
                )
                Result.Success(user)
            }  else if(response.code() == 400) {
                Result.Error("Email sudah terdaftar, silahkan login.", null)
            }else {
                Result.Error("Register Failed", null)
            }
        } catch (e: IOException) {
            Timber.d("IOException: " + e.message)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d("HttpException: " + e.message)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            userPreferencesDataStore.deleteUserData()
            Result.Success(Unit)
        } catch (e: IOException) {
            Timber.d("IOException: " + e.message)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }
}