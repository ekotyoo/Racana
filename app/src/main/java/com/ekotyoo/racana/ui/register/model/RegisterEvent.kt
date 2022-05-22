package com.ekotyoo.racana.ui.register.model

sealed class RegisterEvent {
    object RegisterSuccess : RegisterEvent()
    data class RegisterFailed(val message: String) : RegisterEvent()
    object NavigateToLoginScreen : RegisterEvent()
}