package com.ekotyoo.racana.ui.register

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

    fun onNameTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(nameTextFieldValue = value)
    }

    fun onEmailTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(emailTextFieldValue = value)
    }

    fun onPasswordTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(passwordTextFieldValue = value)
    }

    fun onConfirmPasswordTextFieldValueChange(value: String) {
        _state.value = _state.value.copy(confirmPasswordTextFieldValue = value)
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
}

data class RegisterScreenState(
    val nameTextFieldValue: String = "",
    val emailTextFieldValue: String = "",
    val passwordTextFieldValue: String = "",
    val confirmPasswordTextFieldValue: String = ""
)

sealed class RegisterScreenEvent {
    object RegisterSuccess : RegisterScreenEvent()
    object NavigateToLoginScreen : RegisterScreenEvent()
}