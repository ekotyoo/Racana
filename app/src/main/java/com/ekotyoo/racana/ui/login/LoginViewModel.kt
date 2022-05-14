package com.ekotyoo.racana.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state

    private val _eventChannel = Channel<LoginScreenEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private suspend fun login(email: String, password: String) {
        // TODO: Implement login functionality
        _eventChannel.send(LoginScreenEvent.LoginSuccess)
    }

    fun onEmailTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(emailTextFieldValue = value)
        if (value.isNotEmpty()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(value).matches())
                _state.value = _state.value.copy(emailErrorMessage = "error")
            else
                _state.value = _state.value.copy(emailErrorMessage = null)
        }
        else
            _state.value = _state.value.copy(emailErrorMessage = null)
    }

    fun onPasswordTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(passwordTextFieldValue = value)
        if (value.isNotEmpty()) {
            if (value.length < 8)
                _state.value = _state.value.copy(passwordErrorMessage = "error")
            else
                _state.value = _state.value.copy(passwordErrorMessage = null)
        }
        else
            _state.value = _state.value.copy(passwordErrorMessage = null)
    }

    fun onLoginButtonClicked() {
        val email = state.value.emailTextFieldValue
        val password = state.value.passwordTextFieldValue
        viewModelScope.launch {
            login(email, password)
        }
    }

    fun onRegisterTextClicked() {
        viewModelScope.launch {
            _eventChannel.send(LoginScreenEvent.NavigateToRegisterScreen)
        }
    }
}

data class LoginScreenState(
    val emailTextFieldValue: String = "",
    val passwordTextFieldValue: String = "",
    val emailErrorMessage: String? = "",
    val passwordErrorMessage: String? = ""
)

sealed class LoginScreenEvent {
    object LoginSuccess : LoginScreenEvent()
    object NavigateToRegisterScreen: LoginScreenEvent()
}