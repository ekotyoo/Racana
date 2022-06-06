package com.ekotyoo.racana.ui.main.articlelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.data.model.Article
import com.ekotyoo.racana.ui.main.articlelist.model.ArticleListEvent
import com.ekotyoo.racana.ui.main.articlelist.model.ArticleListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ArticleListState(getDummyArticle(), false))
    val state: StateFlow<ArticleListState> = _state

    private val _eventChannel = Channel<ArticleListEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onArticleClicked() {
        viewModelScope.launch {
            _eventChannel.send(ArticleListEvent.NavigateToArticleDetail)
        }
    }
}

fun getDummyArticle() : List<Article> {
    val list = mutableListOf<Article>()
    for (i in 0..10) {
        list.add(Article(
            id = i,
            title = "Judul $i",
            imageUrl = "https://picsum.photos/200/300",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas feugiat eros ut pretium molestie. Nulla facilisi. Fusce molestie posuere dolor. Sed sagittis risus at elit convallis convallis. Phasellus quis turpis sagittis, convallis magna quis, dapibus turpis. Nullam nec nisl a urna malesuada finibus. Suspendisse malesuada sodales odio. Nam nec sem libero. Donec rhoncus varius nibh, in lobortis sapien semper non. Mauris vitae ex dictum, dapibus lacus at, finibus dui. Aliquam velit neque, lobortis non enim sed, sollicitudin viverra orci. In imperdiet auctor posuere. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam non fringilla magna, sed.",
            author = "Author $i",
            source = "www.coolweb$i.com"
        ))
    }
    return list
}