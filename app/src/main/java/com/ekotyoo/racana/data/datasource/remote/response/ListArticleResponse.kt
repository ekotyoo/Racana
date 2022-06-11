package com.ekotyoo.racana.data.datasource.remote.response

data class ListArticleResponse(
	val data: List<DataItem>,
	val message: String,
	val status: String
)

data class DataItem(
	val id: Int,
	val author: String,
	val imageUrl: String,
	val title: String
)

