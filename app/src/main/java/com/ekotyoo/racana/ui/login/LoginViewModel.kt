package com.ekotyoo.racana.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state

    fun onEmailTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(emailTextFieldValue = value)
    }

    fun onPasswordTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(passwordTextFieldValue = value)
    }

    fun onLoginButtonClicked() {
        // TODO: Handle login button clicked
    }
}

data class LoginScreenState(
    val emailTextFieldValue: String = "",
    val passwordTextFieldValue: String = "",
)