package com.ekotyoo.racana.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleDetailResponse(

	@SerializedName("data")
	val data: Data,

	@SerializedName("message")
	val message: String,

	@SerializedName("status")
	val status: String
)

data class Data(

	@SerializedName("author")
	val author: String,

	@SerializedName("imageUrl")
	val imageUrl: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("source")
	val source: String,

	@SerializedName("title")
	val title: String,

	@SerializedName("content")
	val content: String
)

