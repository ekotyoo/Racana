package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ekotyoo.racana.ui.NavGraphs
import com.ekotyoo.racana.ui.appCurrentDestinationAsState
import com.ekotyoo.racana.ui.destinations.MainScreenDestination
import com.ekotyoo.racana.ui.destinations.ProfileScreenDestination
import com.ekotyoo.racana.ui.startAppDestination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@Composable
fun RBottomNavigationBar(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    bottomAppBarNavController: NavController
) {
    val currentDestination =
        bottomAppBarNavController.appCurrentDestinationAsState().value
            ?: NavGraphs.bottom.startAppDestination

    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colors.secondary,
        icons = {
            BottomBarDestination.values().forEach { destination ->
                RBottomAppBarIcon(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    isSelected = currentDestination == destination.direction,
                    imageVector = destination.icon,
                    contentDescription = null,
                    onClick = {
                        bottomAppBarNavController.navigate(destination.direction) {
                            bottomAppBarNavController.popBackStack()
                            launchSingleTop = true
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colors.primary,
                onClick = { }
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    )
}

@Composable
fun RBottomAppBarIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit
) {
    IconButton(
        modifier = if (isSelected) modifier.background(
            color = MaterialTheme.colors.primary,
            shape = CircleShape
        ) else modifier,
        onClick = onClick
    ) {
        Icon(imageVector, contentDescription = contentDescription)
    }
}

@RootNavGraph
@NavGraph
annotation class BottomNavGraph(
    val start: Boolean = false
)

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
) {
    MainScreen(MainScreenDestination, Icons.Filled.Home),
    ProfileScreen(ProfileScreenDestination, Icons.Filled.Person),
}