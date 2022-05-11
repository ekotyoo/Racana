package com.ekotyoo.racana.ui.login

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
    }

    fun onPasswordTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(passwordTextFieldValue = value)
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
)

sealed class LoginScreenEvent {
    object LoginSuccess : LoginScreenEvent()
    object NavigateToRegisterScreen: LoginScreenEvent()
}