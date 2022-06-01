package com.ekotyoo.racana.ui.main.destinationdetail.model

sealed class DestinationDetailEvent {
    object OnFavoriteButtonClicked: DestinationDetailEvent()
}