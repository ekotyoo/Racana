package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ListArticleResponse(
	@SerializedName("data")
	val data: List<DataItem>,

	@SerializedName("message")
	val message: String,

	@SerializedName("status")
	val status: String
)

data class DataItem(
	@SerializedName("id")
	val id: Int,

	@SerializedName("author")
	val author: String,

	@SerializedName("imageUrl")
	val imageUrl: String,

	@SerializedName("title")
	val title: String
)

