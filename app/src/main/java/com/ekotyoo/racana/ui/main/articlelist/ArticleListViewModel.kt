package com.ekotyoo.racana.ui.main.articlelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.ArticleRepository
import com.ekotyoo.racana.ui.main.articlelist.model.ArticleListEvent
import com.ekotyoo.racana.ui.main.articlelist.model.ArticleListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ArticleListState())
    val state: StateFlow<ArticleListState> = _state

    private val _eventChannel = Channel<ArticleListEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        getArticles()
    }

    fun onArticleClicked(id: Int) {
        viewModelScope.launch {
            _eventChannel.send(ArticleListEvent.NavigateToArticleDetail(id))
        }
    }

    private fun getArticles() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = articleRepository.getAllArticles()) {
                is Result.Success -> {
                    _state.update { it.copy(listArticle = result.value) }
                }
                is Result.Error -> {
                    _eventChannel.send(ArticleListEvent.GetArticlesError)
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}