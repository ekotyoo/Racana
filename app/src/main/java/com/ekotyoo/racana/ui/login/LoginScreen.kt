package com.ekotyoo.racana.ui.login

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.REditText
import com.ekotyoo.racana.core.composables.RFilledButton
import com.ekotyoo.racana.core.composables.RLoadingOverlay
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.destinations.LoginScreenDestination
import com.ekotyoo.racana.ui.destinations.MainScreenDestination
import com.ekotyoo.racana.ui.destinations.RegisterScreenDestination
import com.ekotyoo.racana.ui.login.model.LoginEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Destination(style = NavigationTransition::class)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is LoginEvent.LoginSuccess -> {
                    navigator.navigate(MainScreenDestination()) {
                        popUpTo(LoginScreenDestination) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
                is LoginEvent.LoginFailed -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is LoginEvent.NavigateToRegisterScreen -> {
                    navigator.navigate(RegisterScreenDestination) {
                        popUpTo(LoginScreenDestination)
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Box(
            Modifier.fillMaxSize()
        ) {
            LoginContent(
                emailValue = state.emailTextFieldValue,
                passwordValue = state.passwordTextFieldValue,
                emailErrorMessage = state.emailErrorMessage,
                passwordErrorMessage = state.passwordErrorMessage,
                isPasswordObscured = state.isPasswordObscured,
                onEmailEmailTextFieldChange = viewModel::onEmailTextFieldValueChange,
                onPasswordTextFieldChange = viewModel::onPasswordTextFieldValueChange,
                onHideShowPasswordToggled = viewModel::onHideShowPasswordToggled,
                onLoginButtonClicked = viewModel::onLoginButtonClicked,
                onRegisterTextClicked = viewModel::onRegisterTextClicked,
            )
            SnackbarHost(hostState = snackbarHostState)
            RLoadingOverlay(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = state.isLoading
            )
        }
    }
}

@Composable
fun LoginContent(
    emailValue: String,
    passwordValue: String,
    emailErrorMessage: String?,
    passwordErrorMessage: String?,
    isPasswordObscured: Boolean = true,
    onHideShowPasswordToggled: () -> Unit = {},
    onEmailEmailTextFieldChange: (String) -> Unit,
    onPasswordTextFieldChange: (String) -> Unit,
    onLoginButtonClicked: () -> Unit,
    onRegisterTextClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(
                scrollState,
                enabled = true
            )
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pointing_map))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever
        )

        Spacer(modifier = Modifier.size(size = 16.dp))
        Text(
            text = stringResource(id = R.string.welcome),
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.size(size = 32.dp))
        LottieAnimation(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.CenterHorizontally),
            composition = composition,
            progress = progress
        )
        Text(
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.size(size = 32.dp))

        //Email
        val isEmailError = !emailErrorMessage.isNullOrEmpty()
        REditText(
            modifier = Modifier.fillMaxWidth(),
            value = emailValue,
            placeholderString = stringResource(id = R.string.email),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "",
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            onValueChange = onEmailEmailTextFieldChange,
            isError = isEmailError
        )
        AnimatedVisibility(isEmailError) {
            Text(
                text = stringResource(id = R.string.email_not_valid),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.size(size = 16.dp))

        //Password
        val isPasswordError = !passwordErrorMessage.isNullOrEmpty()
        REditText(
            modifier = Modifier.fillMaxWidth(),
            value = passwordValue,
            placeholderString = stringResource(id = R.string.password),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "",
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                IconButton(onClick = onHideShowPasswordToggled) {
                    Icon(
                        imageVector = if (isPasswordObscured) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (isPasswordObscured) PasswordVisualTransformation() else VisualTransformation.None,
            onValueChange = onPasswordTextFieldChange,
            isError = isPasswordError
        )
        AnimatedVisibility(isPasswordError) {
            Text(
                text = stringResource(id = R.string.password_less_eight),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.size(size = 32.dp))

        //Login Button
        val buttonEnabled =
            !(isPasswordError || isEmailError || emailValue.isBlank() || passwordValue.isBlank())
        RFilledButton(
            onClick = onLoginButtonClicked,
            placeholderString = stringResource(id = R.string.login),
            enabled = buttonEnabled
        )
        Spacer(modifier = Modifier.size(size = 16.dp))

        //Forgot PW
        val context = LocalContext.current
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(context,
                            "Fitur belum tersedia.",
                            Toast.LENGTH_SHORT)
                        .show()
                },
            text = stringResource(id = R.string.forgot_password),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))

        //Register Option
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.dont_have_an_account))
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                modifier = Modifier.clickable(onClick = onRegisterTextClicked),
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Spacer(modifier = Modifier.size(size = 32.dp))
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode Preview")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode Preview")
@Composable
fun LoginScreenPreview() {
    RacanaTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            LoginContent(
                emailValue = "",
                passwordValue = "",
                emailErrorMessage = "",
                passwordErrorMessage = "",
                onLoginButtonClicked = {},
                onPasswordTextFieldChange = {},
                onEmailEmailTextFieldChange = {},
                onRegisterTextClicked = {}
            )
        }
    }
}