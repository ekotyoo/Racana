package com.ekotyoo.racana.ui.main.articlelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RImageCard
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.data.model.Article
import com.ekotyoo.racana.ui.destinations.ArticleDetailScreenDestination
import com.ekotyoo.racana.ui.main.articlelist.model.ArticleListEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(style = NavigationTransition::class)
@Composable
fun ArticleListScreen(
    navigator: DestinationsNavigator,
    viewModel: ArticleListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect {event ->
            when(event) {
                ArticleListEvent.NavigateToArticleDetail -> {
                    navigator.navigate(ArticleDetailScreenDestination)
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RTopAppBar(
                title = stringResource(id = R.string.traveler_stories),
                isBackButtonAvailable = true,
                onBackButtonCLicked = { navigator.popBackStack() }
            )
        }
    ) {
        ArticleListContent(
            listArticle = state.listArticle,
            onArticleClicked = viewModel::onArticleClicked
        )
    }
}

@Composable
fun ArticleListContent(
    listArticle: List<Article>,
    onArticleClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(listArticle, key = { it.id }) { article ->
            RImageCard(
                imageUrl = article.imageUrl,
                title = article.title,
                description = article.content,
                onClick = onArticleClicked
            )
        }
    }    
}