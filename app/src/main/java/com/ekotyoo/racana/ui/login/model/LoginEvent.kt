package com.ekotyoo.racana.ui.login.model

sealed class LoginEvent {
    object LoginSuccess : LoginEvent()
    data class LoginFailed(val message: String): LoginEvent()
    object NavigateToRegisterScreen : LoginEvent()
}