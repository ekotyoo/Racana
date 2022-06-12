package com.ekotyoo.racana.ui.main.articledetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RLoadingOverlay
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.data.model.Article
import com.ekotyoo.racana.ui.main.articledetail.model.ArticleDetailArgument
import com.ekotyoo.racana.ui.main.articledetail.model.ArticleDetailEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage

@Destination(
    style = NavigationTransition::class,
    navArgsDelegate = ArticleDetailArgument::class
)
@Composable
fun ArticleDetailScreen(
    navigator: DestinationsNavigator,
    viewModel: ArticleDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                ArticleDetailEvent.GetArticleDetailError -> {
                    navigator.navigateUp()
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
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
            ArticleDetailContent(article = state.article)
        }
        RLoadingOverlay(modifier = Modifier.align(Alignment.Center), visible = state.isLoading)
    }
}

@Composable
fun ArticleDetailContent(
    article: Article,
) {
    Column(
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 8.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        CoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.42f)
                .clip(MaterialTheme.shapes.small),
            imageModel = article.imageUrl,
            contentDescription = null,
            previewPlaceholder = R.drawable.ic_launcher_background,
            placeHolder = ImageBitmap.imageResource(id = R.drawable.image_placeholder),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.title,
            style = MaterialTheme.typography.h6
        )
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colors.secondary)
                .padding(horizontal = 8.dp),
            text = "Oleh : ${article.author}",
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.onSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.content,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sumber : ${article.source}",
            style = MaterialTheme.typography.body2
                .copy(fontWeight = FontWeight.SemiBold)
        )
    }
}