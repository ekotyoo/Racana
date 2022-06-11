package com.ekotyoo.racana.ui.main.dashboard

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RDestinationCard
import com.ekotyoo.racana.core.composables.REmptyIndicator
import com.ekotyoo.racana.core.composables.RIconButton
import com.ekotyoo.racana.core.composables.RImageCard
import com.ekotyoo.racana.core.navigation.BottomNavGraph
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.navigation.RootNavigator
import com.ekotyoo.racana.core.utils.currencyFormatter
import com.ekotyoo.racana.data.model.ArticlePreview
import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.ui.destinations.*
import com.ekotyoo.racana.ui.main.dashboard.model.DashboardEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.skydoves.landscapist.coil.CoilImage

@BottomNavGraph(start = true)
@Destination(style = NavigationTransition::class)
@Composable
fun DashboardScreen(
    rootNavigator: RootNavigator,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()
    val appBarExpanded = derivedStateOf {
        lazyListState.firstVisibleItemIndex == 0
    }

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is DashboardEvent.NavigateToDetailDestination -> {
                    rootNavigator.value.navigate(DestinationDetailScreenDestination(event.id)) {
                        launchSingleTop = true
                    }
                }
                is DashboardEvent.NavigateToTourPlanDetail -> {
                    rootNavigator.value.navigate(TourPlanDetailSavedScreenDestination(event.tourPlan)) {
                        launchSingleTop = true
                    }
                }
                is DashboardEvent.NavigateToDetailArticle -> {
                    rootNavigator.value.navigate(ArticleDetailScreenDestination(event.id)) {
                        launchSingleTop = true
                    }
                }
                is DashboardEvent.AllDestinationClicked -> {
                    rootNavigator.value.navigate(ListDestinationScreenDestination) {
                        launchSingleTop = true
                    }
                }
                is DashboardEvent.AllArticleClicked -> {
                    rootNavigator.value.navigate(ArticleListScreenDestination) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val context = LocalContext.current
            DashboardAppBar(
                userName = state.user?.name,
                expanded = appBarExpanded.value
            ) {
                Toast.makeText(context, "Fitur ini belum tersedia.", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        DashboardContent(
            destinations = state.destinations,
            articles = state.articles,
            lazyListState = lazyListState,
            isLoading = state.isLoading,
            onDestinationClick = viewModel::onDestinationClicked,
            onArticleClick = viewModel::onArticleClicked,
            onTourPlanClick = viewModel::onTourPlanClicked,
            onAllDestinationClicked = viewModel::allDestinationClicked,
            onAllArticleClicked = viewModel::allArticleCLicked,
            tourPlan = state.activeTourPlan
        )
    }
}

@Composable
fun DashboardContent(
    tourPlan: TourPlan?,
    destinations: List<TravelDestination>,
    articles: List<ArticlePreview>,
    onDestinationClick: (Int) -> Unit = {},
    onArticleClick: (Int) -> Unit = {},
    onTourPlanClick: () -> Unit = {},
    lazyListState: LazyListState,
    isLoading: Boolean,
    onAllDestinationClicked: () -> Unit,
    onAllArticleClicked: () -> Unit,
) {
    LazyColumn(
        state = lazyListState,
    ) {
        item {
            DashboardHeader(isLoading = isLoading, tourPlan, onClick = {
                tourPlan?.id?.let {
                    onTourPlanClick()
                }
            })
            Spacer(Modifier.height(16.dp))
        }
        item {
            DashboardSection(
                title = stringResource(id = R.string.top_destination),
                onAllItemShowClicked = onAllDestinationClicked
            ) {
                DestinationRow(
                    destinations = destinations,
                    isLoading = isLoading,
                    onItemClick = onDestinationClick,
                )
            }
            Spacer(Modifier.height(16.dp))
        }
        item {
            DashboardSection(
                title = stringResource(id = R.string.traveler_stories),
                onAllItemShowClicked = onAllArticleClicked
            ) {
                ArticleRow(articles = articles, onItemClick = onArticleClick, isLoading = isLoading)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DashboardAppBar(
    userName: String? = null,
    expanded: Boolean = false,
    onActionClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(targetState = expanded) { target ->
            if (target) {
                Column {
                    Text(
                        text = if (userName != null) "Hai, $userName!" else "Halo!",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Text(
                        text = stringResource(id = R.string.enjoy_your_tour),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            } else {
                Column {
                    Text(
                        text = stringResource(id = R.string.racana),
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Text(
                        text = stringResource(id = R.string.your_tour_friend),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
        RIconButton(
            imageVector = Icons.Rounded.Notifications,
            onClick = onActionClicked,
            contentDescription = "Tombol Notifikasi"
        )
    }
}

@Composable
fun DashboardHeader(
    isLoading: Boolean,
    tourPlan: TourPlan?,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.current_tour),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(Modifier.height(16.dp))
            CurrentTourPlanCard(
                modifier = Modifier.aspectRatio(1.58f),
                tourPlan = tourPlan,
                isLoading = isLoading,
                onClick = onClick
            )
        }
    }
}

@Composable
fun DashboardSection(
    modifier: Modifier = Modifier,
    title: String,
    seeAllVisible: Boolean = true,
    onAllItemShowClicked: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
            if (seeAllVisible) {
                Text(
                    text = stringResource(id = R.string.see_all),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.clickable(onClick = onAllItemShowClicked)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        content()
    }
}

@Composable
fun DestinationRow(
    modifier: Modifier = Modifier,
    destinations: List<TravelDestination>,
    isLoading: Boolean = false,
    onItemClick: (Int) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        if (isLoading) {
            items(4) {
                RDestinationCard(
                    name = "",
                    location = "",
                    imageUrl = "",
                    isLoading = isLoading,
                    onClick = { }
                )
            }
        } else {
            items(destinations, key = { it.id }) { destination ->
                RDestinationCard(
                    name = destination.name,
                    location = destination.city,
                    imageUrl = destination.imageUrl,
                    isLoading = isLoading,
                    onClick = { onItemClick(destination.id) }
                )
            }
        }
    }
}

@Composable
fun ArticleRow(
    modifier: Modifier = Modifier,
    articles: List<ArticlePreview>,
    isLoading: Boolean = false,
    onItemClick: (Int) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        if (isLoading) {
            items(4) {
                RImageCard(
                    modifier = Modifier.aspectRatio(4/2f).width(300.dp),
                    imageUrl = "",
                    title = "",
                    description = "",
                    onClick = {},
                    isLoading = true)
            }
        } else {
            items(articles, key = { it.id }) { article ->
                RImageCard(
                    modifier = Modifier.aspectRatio(4/2f).width(300.dp),
                    imageUrl = article.imageUrl,
                    title = article.title,
                    description = article.author,
                    onClick = {
                        onItemClick(article.id)
                    },
                )
            }
        }
    }
}

@Composable
fun CurrentTourPlanCard(
    modifier: Modifier = Modifier,
    tourPlan: TourPlan? = null,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val translateX = rememberInfiniteTransition().animateFloat(initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(1000, easing = FastOutSlowInEasing)))

    val brush = linearGradient(
        colors = gradient,
        start = Offset(200f, 0f),
        end = Offset(x = translateX.value, y = 0f)
    )

    Card(modifier.clickable(onClick = onClick)) {
        if (isLoading) {
            Spacer(modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(brush)
                .padding(8.dp))
        } else {
            if (tourPlan == null) {
                REmptyIndicator(modifier,
                    text = stringResource(id = R.string.active_tour_plan_empty))
            } else {
                Column(modifier = Modifier.padding(8.dp)) {
                    CoilImage(
                        modifier = Modifier
                            .weight(1f)
                            .clip(MaterialTheme.shapes.small)
                            .fillMaxWidth(),
                        imageModel = tourPlan.imageUrl,
                        previewPlaceholder = R.drawable.ic_launcher_background,
                        placeHolder = ImageBitmap.imageResource(id = R.drawable.image_placeholder),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )

                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tourPlan.title ?: "-",
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = currencyFormatter(tourPlan.totalExpense),
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = tourPlan.period,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}