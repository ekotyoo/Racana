package com.ekotyoo.racana.ui.main.destinationdetail.model

sealed class DestinationDetailEvent {
    object OnBackButtonPressed : DestinationDetailEvent()
    object OnFavoriteButtonClicked: DestinationDetailEvent()

}