package com.ekotyoo.racana.ui.favoritedestination.model

import com.ekotyoo.racana.data.model.TravelDestination

data class FavoriteDestinationState (
    val destinations: List<TravelDestination> = listOf()
)