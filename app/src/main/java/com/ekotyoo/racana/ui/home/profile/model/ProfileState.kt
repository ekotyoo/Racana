package com.ekotyoo.racana.ui.home.profile.model

data class ProfileState(
    val profilePictureUrl: String ="",
    val nameTextFieldValue: String = "",
    val emailTextFieldValue: String = "",
    val isPremium: Boolean = false
)
