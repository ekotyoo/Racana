package com.ekotyoo.racana.ui.main.articledetail

import androidx.lifecycle.ViewModel
import com.ekotyoo.racana.ui.main.articledetail.model.ArticleDetailState
import com.ekotyoo.racana.ui.main.articlelist.getDummyArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ArticleDetailState(getDummyArticle()[0], false))
    val state: StateFlow<ArticleDetailState> = _state
}