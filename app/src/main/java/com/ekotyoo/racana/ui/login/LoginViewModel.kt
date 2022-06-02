package com.ekotyoo.racana.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.AuthRepository
import com.ekotyoo.racana.ui.login.model.LoginEvent
import com.ekotyoo.racana.ui.login.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _eventChannel = Channel<LoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = authRepository.login(email, password)) {
                is Result.Success -> _eventChannel.send(LoginEvent.LoginSuccess)
                is Result.Error -> _eventChannel.send(LoginEvent.LoginFailed(result.message))
            }
            _state.value = _state.value.copy(isLoading = false)
        }
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

    fun onLoginButtonClicked() {
        val email = state.value.emailTextFieldValue
        val password = state.value.passwordTextFieldValue
        login(email, password)
    }

    fun onRegisterTextClicked() {
        viewModelScope.launch {
            _eventChannel.send(LoginEvent.NavigateToRegisterScreen)
        }
    }
}