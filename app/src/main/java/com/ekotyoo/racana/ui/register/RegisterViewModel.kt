package com.ekotyoo.racana.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.AuthRepository
import com.ekotyoo.racana.ui.register.model.RegisterEvent
import com.ekotyoo.racana.ui.register.model.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private val _eventChannel = Channel<RegisterEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private fun register(
        name: String,
        email: String,
        password: String,
    ) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = authRepository.register(name, email, password)) {
                is Result.Success -> _eventChannel.send(RegisterEvent.RegisterSuccess)
                is Result.Error -> _eventChannel.send(RegisterEvent.RegisterFailed(result.message))
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onNameTextFieldValueChange(value: String) {
        _state.update { it.copy(nameTextFieldValue = value) }
        val errorMessage = if (value.isNotEmpty()) {
            when {
                value[0] == ' ' -> START_WHITESPACE
                value.contains("  ") -> DOUBLE_WHITESPACE
                !value.matches("^[a-zA-Z ]*$".toRegex()) -> NON_ALPHABET
                else -> null
            }
        } else null
        _state.update { it.copy(nameErrorMessage = errorMessage) }
    }

    fun onEmailTextFieldValueChange(value: String) {
        _state.update { it.copy(emailTextFieldValue = value) }
        val errorMessage = if (value.isNotEmpty()) {
            when {
                !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> "error"
                else -> null
            }
        } else null
        _state.update { it.copy(emailErrorMessage = errorMessage) }
    }

    fun onPasswordTextFieldValueChange(value: String) {
        _state.update { it.copy(passwordTextFieldValue = value) }
        val errorMessage = if (value.isNotEmpty()) {
            when {
                value.length < 8 -> "error"
                else -> null
            }
        } else null
        _state.update { it.copy(passwordErrorMessage = errorMessage) }
    }

    fun onHideShowPasswordToggled() {
        _state.update { it.copy(isPasswordObscured = !_state.value.isPasswordObscured) }
    }

    fun onRegisterButtonClicked() {
        val name = state.value.nameTextFieldValue
        val email = state.value.emailTextFieldValue
        val password = state.value.passwordTextFieldValue

        register(
            name,
            email,
            password,
        )
    }

    fun onLoginTextClicked() {
        viewModelScope.launch {
            _eventChannel.send(RegisterEvent.NavigateToLoginScreen)
        }
    }

    companion object {
        const val START_WHITESPACE = "start_whitespace"
        const val DOUBLE_WHITESPACE = "double_whitespace"
        const val NON_ALPHABET = "non_alphabet"
    }
}