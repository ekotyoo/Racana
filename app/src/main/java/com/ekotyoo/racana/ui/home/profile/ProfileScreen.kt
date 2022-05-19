package com.ekotyoo.racana.ui.home.profile

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.ui.NavigationTransition
import com.ekotyoo.racana.core.composables.BottomNavGraph
import com.ekotyoo.racana.core.composables.ROutlinedButton
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage

@BottomNavGraph
@Destination(style = NavigationTransition::class)
@Composable
fun AnimatedVisibilityScope.ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {

            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        ProfileContent(
            state.profilePictureUrl,
            state.nameTextFieldValue,
            state.emailTextFieldValue,
            state.isPremium,
            viewModel::onMyPlanButtonClicked,
            viewModel::onFavoriteDestinationButtonClicked,
            viewModel::onSettingsButtonClicked,
            viewModel::onLogOutButtonClicked
        )
    }
}

@Composable
fun ProfileContent(
    profilePictureUrl: String,
    nameTextFieldValue: String,
    emailTextFieldValue: String,
    isPremium: Boolean,
    onMyPlanButtonCLicked: () -> Unit,
    onFavoriteDestinationButtonCLicked: () -> Unit,
    onSettingsButtonCLicked: () -> Unit,
    onLogOutButtonClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            RTopAppBar(title = stringResource(id = R.string.profile))
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                CoilImage(
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    imageModel = profilePictureUrl
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = nameTextFieldValue,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = emailTextFieldValue,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                ProfileButton(
                    text = stringResource(id = R.string.my_plan),
                    icon = Icons.Default.List,
                    onClick = onMyPlanButtonCLicked
                )
                ProfileButton(
                    text = stringResource(id = R.string.favorite_destination),
                    icon = Icons.Default.Favorite,
                    onClick = onFavoriteDestinationButtonCLicked
                )
                ProfileButton(
                    text = stringResource(id = R.string.settings),
                    icon = Icons.Default.Settings,
                    onClick = onSettingsButtonCLicked
                )
                Spacer(modifier = Modifier.weight(1f))
                ROutlinedButton(
                    placeholderString = stringResource(id = R.string.logout),
                    onClick = onLogOutButtonClicked
                )
            }
        }
    )
}

@Composable
fun ProfileButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(modifier) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable(onClick = onClick)
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(36.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.primary,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(4.dp),
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = text,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(4.dp),
                tint = MaterialTheme.colors.onPrimary,
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Preview(
    name = "Light Mode Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun RegisterScreenPreview() {
    RacanaTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            ProfileContent(
                profilePictureUrl = "",
                nameTextFieldValue = "",
                emailTextFieldValue = "",
                isPremium = false,
                {},
                {},
                {},
                {}
            )
        }
    }
}
