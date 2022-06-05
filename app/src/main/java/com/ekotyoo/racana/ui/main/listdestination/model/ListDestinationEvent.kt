package com.ekotyoo.racana.ui.main.listdestination.model

sealed class ListDestinationEvent {
    data class NavigateToDetailDestination(val id: Int) : ListDestinationEvent()
    object GetDestinationsError : ListDestinationEvent()
}