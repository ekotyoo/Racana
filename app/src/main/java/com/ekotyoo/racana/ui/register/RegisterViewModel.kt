package com.ekotyoo.racana.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegisterScreenState())
    val state: StateFlow<RegisterScreenState> = _state

    private val _eventChannel = Channel<RegisterScreenEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private suspend fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        // TODO: Implement register functionality
        _eventChannel.send(RegisterScreenEvent.RegisterSuccess)
    }

    fun onNameTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(nameTextFieldValue = value)
        val errorMessage = if (value.isNotEmpty()) {
            when {
                value[0] == ' ' -> START_WHITESPACE
                value.contains("  ") -> DOUBLE_WHITESPACE
                !value.matches("^[a-zA-Z ]*$".toRegex()) -> NON_ALPHABET
                else -> null
            }
        } else null
        _state.value = _state.value.copy(nameErrorMessage = errorMessage)
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

    fun onConfirmPasswordTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(confirmPasswordTextFieldValue = value)
        val errorMessage = if (value.isNotEmpty()) {
            when {
                value != _state.value.passwordTextFieldValue -> "error"
                else -> null
            }
        } else null
        _state.value = _state.value.copy(confirmPasswordErrorMessage = errorMessage)
    }

    fun onRegisterButtonClicked() {
        val name = state.value.nameTextFieldValue
        val email = state.value.emailTextFieldValue
        val password = state.value.passwordTextFieldValue
        val confirmPassword = state.value.confirmPasswordTextFieldValue
        viewModelScope.launch {
            register(
                name,
                email,
                password,
                confirmPassword
            )
        }
    }

    fun onLoginTextClicked() {
        viewModelScope.launch {
            _eventChannel.send(RegisterScreenEvent.NavigateToLoginScreen)
        }
    }

    companion object {
        const val START_WHITESPACE = "start_whitespace"
        const val DOUBLE_WHITESPACE = "double_whitespace"
        const val NON_ALPHABET = "non_alphabet"
    }
}

data class RegisterScreenState(
    val nameTextFieldValue: String = "",
    val emailTextFieldValue: String = "",
    val passwordTextFieldValue: String = "",
    val confirmPasswordTextFieldValue: String = "",
    val nameErrorMessage: String? = "",
    val emailErrorMessage: String? = "",
    val passwordErrorMessage: String? = "",
    val confirmPasswordErrorMessage: String? = ""
)

sealed class RegisterScreenEvent {
    object RegisterSuccess : RegisterScreenEvent()
    object NavigateToLoginScreen : RegisterScreenEvent()
}