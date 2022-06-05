package com.ekotyoo.racana.ui.favoritedestination.model

sealed class FavoriteDestinationEvent {
    data class OnDestinationClicked(val id: Int) : FavoriteDestinationEvent();

}