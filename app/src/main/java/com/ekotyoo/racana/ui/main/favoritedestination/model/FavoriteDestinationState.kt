package com.ekotyoo.racana.ui.main.favoritedestination.model

import com.ekotyoo.racana.data.model.TravelDestination

data class FavoriteDestinationState (
    val isLoading: Boolean = false,
    val destinations: List<TravelDestination> = listOf()
)