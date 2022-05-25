package com.ekotyoo.racana.ui.main.profile.model

import com.ekotyoo.racana.data.model.UserModel

data class ProfileState(
    val user: UserModel? = null,
    val isPremium: Boolean = false
)
