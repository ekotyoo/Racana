package com.ekotyoo.racana.ui.register

import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
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

    private suspend fun register(name: String, email: String, password: String, confirmPassword: String) {
        _eventChannel.send(RegisterScreenEvent.RegisterSuccess)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun onNameTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(nameTextFieldValue = value)
        if (value.isNotEmpty()) {
            if (value[0] == ' ')
                _state.value = _state.value.copy(nameErrorMessage = START_WHITESPACE)
            else if (value.contains("  "))
                _state.value = _state.value.copy(nameErrorMessage = DOUBLE_WHITESPACE)
            else if (!value.matches("^[a-zA-Z ]*$".toRegex()))
                _state.value = _state.value.copy(nameErrorMessage = NON_ALPHABET)
            else
                _state.value = _state.value.copy(nameErrorMessage = null)
        }
        else
            _state.value = _state.value.copy(nameErrorMessage = null)
        
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

    fun onConfirmPasswordTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(confirmPasswordTextFieldValue = value)
        if (value.isNotEmpty()) {
            if (value != _state.value.passwordTextFieldValue)
                _state.value = _state.value.copy(confirmPasswordErrorMessage = "error")
            else
                _state.value = _state.value.copy(confirmPasswordErrorMessage = null)
        }
        else
            _state.value = _state.value.copy(confirmPasswordErrorMessage = null)
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