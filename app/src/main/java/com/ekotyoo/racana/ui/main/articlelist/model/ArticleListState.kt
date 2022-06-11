package com.ekotyoo.racana.ui.main.articlelist.model

import com.ekotyoo.racana.data.model.ArticlePreview

data class ArticleListState(
    val listArticle: List<ArticlePreview> = emptyList(),
    val isLoading: Boolean = false,
)