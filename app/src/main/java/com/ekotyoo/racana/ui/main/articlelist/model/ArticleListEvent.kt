package com.ekotyoo.racana.ui.main.articlelist.model

sealed class ArticleListEvent {
    object NavigateToArticleDetail : ArticleListEvent()
}