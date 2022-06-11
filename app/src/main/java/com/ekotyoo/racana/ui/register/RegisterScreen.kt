package com.ekotyoo.racana.ui.register

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.ekotyoo.racana.ui.register.model.RegisterEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Destination(style = NavigationTransition::class)
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is RegisterEvent.RegisterSuccess -> {
                    snackbarHostState.showSnackbar("Akun berhasil dibuat, silahkan login!")
                    navigator.navigate(LoginScreenDestination) {
                        popUpTo(LoginScreenDestination) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
                is RegisterEvent.RegisterFailed -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is RegisterEvent.NavigateToLoginScreen -> {
                    navigator.navigate(LoginScreenDestination) {
                        popUpTo(LoginScreenDestination) {
                            inclusive = true
                        }
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
            RegisterContent(
                state.nameTextFieldValue,
                state.emailTextFieldValue,
                state.passwordTextFieldValue,
                state.nameErrorMessage,
                state.emailErrorMessage,
                state.passwordErrorMessage,
                state.isPasswordObscured,
                viewModel::onNameTextFieldValueChange,
                viewModel::onEmailTextFieldValueChange,
                viewModel::onPasswordTextFieldValueChange,
                viewModel::onHideShowPasswordToggled,
                viewModel::onRegisterButtonClicked,
                viewModel::onLoginTextClicked
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
fun RegisterContent(
    nameValue: String,
    emailValue: String,
    passwordValue: String,
    nameErrorMessage: String?,
    emailErrorMessage: String?,
    passwordErrorMessage: String?,
    isPasswordObscured: Boolean,
    onNameTextFieldChange: (String) -> Unit,
    onEmailTextFieldChange: (String) -> Unit,
    onPasswordTextFieldChange: (String) -> Unit,
    onHideShowPasswordToggled: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    onLoginTextClicked: () -> Unit
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
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pointing_calendar))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
        )

        Spacer(modifier = Modifier.size(size = 16.dp))
        Text(
            text = stringResource(id = R.string.create_account),
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.size(size = 28.dp))
        LottieAnimation(
            modifier = Modifier
                .size(240.dp)
                .align(CenterHorizontally),
            composition = composition,
            progress = progress
        )
        Text(
            text = stringResource(id = R.string.register),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.size(size = 28.dp))

        //Name
        val isNameError = !nameErrorMessage.isNullOrEmpty()
        REditText(
            modifier = Modifier.fillMaxWidth(),
            value = nameValue,
            placeholderString = stringResource(id = R.string.name),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = onNameTextFieldChange,
            isError = isNameError
        )
        AnimatedVisibility(isNameError) {
            val errorMessage =
                when (nameErrorMessage) {
                    RegisterViewModel.START_WHITESPACE ->
                        stringResource(id = R.string.name_not_started_with_whitespace)
                    RegisterViewModel.DOUBLE_WHITESPACE ->
                        stringResource(id = R.string.name_cannot_containt_double_space)
                    RegisterViewModel.NON_ALPHABET ->
                        stringResource(id = R.string.name_cannot_containt_non_alphabetic_character)
                    else ->
                        stringResource(id = R.string.name_not_valid)
                }
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
            )
        }
        Spacer(modifier = Modifier.size(size = 16.dp))

        //Email
        val isEmailError = !emailErrorMessage.isNullOrEmpty()
        REditText(
            modifier = Modifier.fillMaxWidth(),
            value = emailValue,
            placeholderString = stringResource(id = R.string.email),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            onValueChange = onEmailTextFieldChange,
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
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = onHideShowPasswordToggled) {
                    Icon(
                        imageVector = if (isPasswordObscured) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
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

        //Register Button
        val buttonEnabled =
            !(isNameError || isEmailError || isPasswordError || nameValue.isBlank() || emailValue.isBlank() || passwordValue.isBlank())
        RFilledButton(
            onClick = onRegisterButtonClicked,
            placeholderString = stringResource(id = R.string.register),
            enabled = buttonEnabled
        )
        Spacer(modifier = Modifier.weight(1f))

        //Login Option
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.already_have_account))
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                modifier = Modifier.clickable { onLoginTextClicked() },
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Spacer(modifier = Modifier.size(size = 32.dp))
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
            RegisterContent(
                nameValue = "",
                emailValue = "",
                passwordValue = "",
                nameErrorMessage = "",
                emailErrorMessage = "",
                passwordErrorMessage = "",
                isPasswordObscured = true,
                onNameTextFieldChange = {},
                onEmailTextFieldChange = {},
                onPasswordTextFieldChange = {},
                onHideShowPasswordToggled = {},
                onRegisterButtonClicked = {},
                onLoginTextClicked = {}
            )
        }
    }
}