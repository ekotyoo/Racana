package com.ekotyoo.racana.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

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
        val errorMessage = if (value.isNotEmpty()) {
            when {
                !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> "error"
                else -> null
            }
        } else null
        _state.value = _state.value.copy(emailErrorMessage = errorMessage)
    }

    fun onPasswordTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(passwordTextFieldValue = value)
        val errorMessage = if (value.isNotEmpty()) {
            when {
                value.length < 8 -> "error"
                else -> null
            }
        } else null
        _state.value = _state.value.copy(passwordErrorMessage = errorMessage)
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
    object NavigateToRegisterScreen : LoginScreenEvent()
}