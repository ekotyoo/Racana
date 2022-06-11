package com.ekotyoo.racana.ui.main.articledetail.model

sealed class ArticleDetailEvent() {
    object GetArticleDetailError : ArticleDetailEvent()
}
