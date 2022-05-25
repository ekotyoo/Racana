package com.ekotyoo.racana.ui.main.profile.model

sealed class ProfileEvent {
    object NavigateToMyPlan : ProfileEvent()
    object NavigateToFavoriteDestination : ProfileEvent()
    object NavigateToSettings : ProfileEvent()
    object LogOutSuccess : ProfileEvent()
    object LogOutFailed : ProfileEvent()
}