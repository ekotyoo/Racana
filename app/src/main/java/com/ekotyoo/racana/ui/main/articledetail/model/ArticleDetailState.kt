package com.ekotyoo.racana.ui.main.articledetail.model

import com.ekotyoo.racana.data.model.Article

data class ArticleDetailState(
    val article: Article = Article(),
    val isLoading: Boolean = false
)