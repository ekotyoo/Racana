package com.ekotyoo.racana.ui.main.articledetail

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.data.model.Article
import com.ekotyoo.racana.ui.main.articlelist.getDummyArticle
import com.ekotyoo.racana.ui.main.destinationdetail.DestinationDetailContent
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetail
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetailState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage

@Destination(style = NavigationTransition::class)
@Composable
fun ArticleDetailScreen(
    navigator: DestinationsNavigator,
    viewModel: ArticleDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

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
}

@Composable
fun ArticleDetailContent(
    article: Article
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
            text = "Oleh ${article.author}",
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.content,
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sumber : ${article.source}",
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode Preview"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode Preview"
)
@Composable
fun CounterPreview() {
    RacanaTheme {
        ArticleDetailContent(getDummyArticle()[0])
    }
}