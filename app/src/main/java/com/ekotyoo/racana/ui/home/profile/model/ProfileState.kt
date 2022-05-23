package com.ekotyoo.racana.ui.home.profile.model

import com.ekotyoo.racana.data.model.UserModel

data class ProfileState(
    val user: UserModel? = null,
    val isPremium: Boolean = false
)
