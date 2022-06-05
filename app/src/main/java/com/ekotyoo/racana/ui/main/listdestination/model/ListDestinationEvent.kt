package com.ekotyoo.racana.ui.main.listdestination.model

sealed class ListDestinationEvent {
    data class OnDestinationClicked(val id: Int) : ListDestinationEvent();
}