package com.ekotyoo.racana.core.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ekotyoo.racana.ui.NavGraphs
import com.ekotyoo.racana.ui.appCurrentDestinationAsState
import com.ekotyoo.racana.ui.destinations.*
import com.ekotyoo.racana.ui.startAppDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@Composable
fun RBottomNavigationBar(
    modifier: Modifier = Modifier,
    rootNavigator: DestinationsNavigator,
    bottomAppBarNavController: NavController,
) {
    val currentDestination =
        bottomAppBarNavController.appCurrentDestinationAsState().value
            ?: NavGraphs.bottom.startAppDestination

    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colors.surface,
        icons = {
            BottomBarDestination.values().forEach { destination ->
                RBottomAppBarIcon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    isSelected = currentDestination == destination.direction,
                    imageVector = destination.icon,
                    contentDescription = null,
                    onClick = {
                        if (currentDestination != destination.direction) {
                            bottomAppBarNavController.navigate(destination.direction) {
                                popUpTo(DashboardScreenDestination)
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colors.primary,
                onClick = {
                    rootNavigator.navigate(CreateTourPlanScreenDestination)
                }
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RBottomAppBarIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
) {
    val color =
        animateColorAsState(targetValue = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface)

    Box(modifier.size(50.dp), contentAlignment = Alignment.Center) {
        AnimatedVisibility(
            visible = isSelected,
            enter = scaleIn(animationSpec = spring(Spring.DampingRatioMediumBouncy)) + fadeIn(),
            exit = scaleOut() + fadeOut(),
        ) {
            Spacer(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = CircleShape
                    )
                    .fillMaxSize()
            )
        }
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector,
                contentDescription = contentDescription,
                tint = color.value
            )
        }
    }
}

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
) {
    MainScreen(DashboardScreenDestination, Icons.Rounded.Home),
    SearchScreen(SearchScreenDestination, Icons.Rounded.Search),
    TourPlanListScreen(TourPlanListScreenDestination, Icons.Rounded.List),
    ProfileScreen(ProfileScreenDestination, Icons.Rounded.Person)
}