package com.ekotyoo.racana.ui.main.listdestination.model

import com.ekotyoo.racana.data.model.TravelDestination

data class ListDestinationState (
    val isLoading: Boolean = false,
    val destinations: List<TravelDestination> = listOf()
)
