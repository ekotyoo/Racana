package com.ekotyoo.racana.ui.main.articledetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.ArticleRepository
import com.ekotyoo.racana.ui.destinations.ArticleDetailScreenDestination
import com.ekotyoo.racana.ui.main.articledetail.model.ArticleDetailEvent
import com.ekotyoo.racana.ui.main.articledetail.model.ArticleDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ArticleDetailState())
    val state: StateFlow<ArticleDetailState> = _state

    private val _eventChannel =
        Channel<ArticleDetailEvent>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        val args = ArticleDetailScreenDestination.argsFrom(savedStateHandle)
        val id = args.id
        getArticleDetail(id)
    }

    private fun getArticleDetail(id: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = articleRepository.getArticleDetail(id)) {
                is Result.Success -> {
                    _state.update { it.copy(article = result.value) }
                }
                is Result.Error -> {
                    _eventChannel.send(ArticleDetailEvent.GetArticleDetailError)
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}