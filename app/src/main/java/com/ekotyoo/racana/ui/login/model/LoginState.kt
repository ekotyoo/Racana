package com.ekotyoo.racana.ui.login.model

data class LoginState(
    val isLoading: Boolean = false,
    val emailTextFieldValue: String = "",
    val passwordTextFieldValue: String = "",
    val emailErrorMessage: String? = "",
    val passwordErrorMessage: String? = "",
    val isPasswordObscured: Boolean = true,
)