package com.ekotyoo.racana.ui.main.articlelist.model

import com.ekotyoo.racana.data.model.Article

data class ArticleListState(
    val listArticle: List<Article>,
    val isLoading: Boolean
)