package com.ekotyoo.racana.ui.register

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.REditText
import com.ekotyoo.racana.core.composables.RFilledButton
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.NavGraphs
import com.ekotyoo.racana.ui.destinations.HomeScreenDestination
import com.ekotyoo.racana.ui.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.startDestination

@Destination
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                RegisterScreenEvent.RegisterSuccess -> {
                    navigator.navigate(HomeScreenDestination)
                }
                RegisterScreenEvent.NavigateToLoginScreen -> {
                    navigator.navigate(LoginScreenDestination) {
                        popUpTo(NavGraphs.root.startDestination)
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        RegisterContent(
            state.nameTextFieldValue,
            state.emailTextFieldValue,
            state.passwordTextFieldValue,
            state.confirmPasswordTextFieldValue,
            state.nameErrorMessage,
            state.emailErrorMessage,
            state.passwordErrorMessage,
            state.confirmPasswordErrorMessage,
            viewModel::onNameTextFieldValueChange,
            viewModel::onEmailTextFieldValueChange,
            viewModel::onPasswordTextFieldValueChange,
            viewModel::onConfirmPasswordTextFieldValueChange,
            viewModel::onRegisterButtonClicked,
            viewModel::onLoginTextClicked
        )
    }
}

@Composable
fun RegisterContent(
    nameValue: String,
    emailValue: String,
    passwordValue: String,
    confirmPasswordValue: String,
    nameErrorMessage: String?,
    emailErrorMessage: String?,
    passwordErrorMessage: String?,
    confirmPasswordErrorMessage: String?,
    onNameTextFieldChange: (String) -> Unit,
    onEmailTextFieldChange: (String) -> Unit,
    onPasswordTextFieldChange: (String) -> Unit,
    onConfirmPasswordTextFieldChange: (String) -> Unit,
    onRegisterButtonClicked: () -> Unit,
    onLoginTextClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .verticalScroll(
                scrollState,
                enabled = true
            )
    ) {
        Text(
            text = stringResource(id = R.string.create_account),
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.size(size = 28.dp))
        Image(
            modifier = Modifier
                .size(215.dp)
                .align(CenterHorizontally),
            alignment = Center,
            painter = painterResource(id = R.drawable.register_illustration),
            contentDescription = null
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
        if (isNameError) {
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
        } else {
            Spacer(modifier = Modifier.size(size = 20.dp))
        }

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
        if (isEmailError) {
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = onPasswordTextFieldChange,
            isError = isPasswordError
        )
        if (isPasswordError) {
            Text(
                text = stringResource(id = R.string.password_less_eight),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        } else {
            Spacer(modifier = Modifier.size(size = 20.dp))
        }

        //Confirm Password
        val isConfirmPasswordError = !confirmPasswordErrorMessage.isNullOrEmpty()
        REditText(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPasswordValue,
            placeholderString = stringResource(id = R.string.password_confirmation),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = onConfirmPasswordTextFieldChange,
            isError = isConfirmPasswordError
        )
        if (isConfirmPasswordError) {
            Text(
                text = stringResource(id = R.string.password_not_same),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
            )
        } else {
            Spacer(modifier = Modifier.size(size = 32.dp))
        }

        //Register Button
        val buttonEnabled =
            !(isNameError || isEmailError || isPasswordError || isConfirmPasswordError || nameValue.isBlank() || emailValue.isBlank() || passwordValue.isBlank() || confirmPasswordValue.isBlank())
        RFilledButton(
            onClick = onRegisterButtonClicked,
            placeholderString = stringResource(id = R.string.register),
            enabled = buttonEnabled
        )
        Spacer(modifier = Modifier.size(size = 16.dp))

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
                confirmPasswordValue = "",
                nameErrorMessage = "",
                emailErrorMessage = "",
                passwordErrorMessage = "",
                confirmPasswordErrorMessage = "",
                {},
                {},
                {},
                {},
                {},
                {})
        }
    }
}