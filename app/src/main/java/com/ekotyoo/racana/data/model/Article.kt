package com.ekotyoo.racana.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val content: String = "",
    val author: String = "",
    val source: String = ""
) : Parcelable

@Parcelize
data class ArticlePreview(
    val id: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val author: String = "",
) : Parcelable
