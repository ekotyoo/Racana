package com.ekotyoo.racana.ui.main.favoritedestination.model

sealed class FavoriteDestinationEvent {
    data class OnDestinationClicked(val id: Int) : FavoriteDestinationEvent()
}