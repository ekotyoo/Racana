package com.ekotyoo.racana.data.datasource.remote.response

data class ArticleDetailResponse(
	val data: Data,
	val message: String,
	val status: String
)

data class Data(
	val author: String,
	val imageUrl: String,
	val id: Int,
	val source: String,
	val title: String,
	val content: String
)

