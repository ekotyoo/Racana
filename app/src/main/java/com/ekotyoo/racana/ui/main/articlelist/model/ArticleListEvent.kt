package com.ekotyoo.racana.ui.main.articlelist.model

sealed class ArticleListEvent {
    data class NavigateToArticleDetail(val id: Int) : ArticleListEvent()
    object GetArticlesError : ArticleListEvent()
}