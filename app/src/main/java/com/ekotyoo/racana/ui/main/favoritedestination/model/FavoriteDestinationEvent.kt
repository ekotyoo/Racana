package com.ekotyoo.racana.ui.main.favoritedestination.model

sealed class FavoriteDestinationEvent {
    data class NavigateToDetailDestination(val id: Int) : FavoriteDestinationEvent()
    object GetFavoriteDestinationsError : FavoriteDestinationEvent()
}