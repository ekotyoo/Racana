package com.ekotyoo.racana.data.repository

import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.datasource.local.UserPreferencesDataStore
import com.ekotyoo.racana.data.datasource.remote.api.ArticleApi
import com.ekotyoo.racana.data.model.Article
import com.ekotyoo.racana.data.model.ArticlePreview
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class ArticleRepository @Inject constructor(
    private val articleApi: ArticleApi,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) {

    suspend fun getAllArticles(): Result<List<ArticlePreview>> {
        return try {
            val token = userPreferencesDataStore.userData.first().token
            val response = articleApi.getAllArticles(token ?: "")
            val data = response.body()?.data
            if (response.isSuccessful && data != null) {
                val articles = data.map {
                    ArticlePreview(
                        id = it.id,
                        title = it.title,
                        imageUrl = it.imageUrl,
                        author = it.author
                    )
                }
                Result.Success(articles)
            } else {
                Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
            }
        } catch (e: IOException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }

    suspend fun getDashboardArticles(): Result<List<ArticlePreview>> {
        return try {
            val token = userPreferencesDataStore.userData.first().token
            val response = articleApi.getDashboardArticles(token ?: "")
            val data = response.body()?.data
            if (response.isSuccessful && data != null) {
                val articles = data.map {
                    ArticlePreview(
                        id = it.id,
                        title = it.title,
                        imageUrl = it.imageUrl,
                        author = it.author
                    )
                }
                Result.Success(articles)
            } else {
                Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
            }
        } catch (e: IOException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }

    suspend fun getArticleDetail(id: Int): Result<Article> {
        return try {
            val token = userPreferencesDataStore.userData.first().token
            val response = articleApi.getArticleDetailById(token ?: "", id)
            val data = response.body()?.data
            if (response.isSuccessful && data != null) {
                val article = Article(
                    id = data.id,
                    title = data.title,
                    imageUrl = data.imageUrl,
                    author = data.author,
                    content = data.content,
                    source = data.source
                )
                Result.Success(article)
            } else {
                Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
            }
        } catch (e: IOException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }
}