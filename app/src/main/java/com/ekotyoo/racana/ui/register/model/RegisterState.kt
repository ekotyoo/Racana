package com.ekotyoo.racana.ui.register.model

data class RegisterState(
    val isLoading: Boolean = false,
    val isPasswordObscured: Boolean = true,
    val nameTextFieldValue: String = "",
    val emailTextFieldValue: String = "",
    val passwordTextFieldValue: String = "",
    val nameErrorMessage: String? = "",
    val emailErrorMessage: String? = "",
    val passwordErrorMessage: String? = "",
)