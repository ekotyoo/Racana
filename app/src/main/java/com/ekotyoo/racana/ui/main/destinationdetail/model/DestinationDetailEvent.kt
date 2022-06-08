package com.ekotyoo.racana.ui.main.destinationdetail.model

sealed class DestinationDetailEvent {
    object GetDestinationDetailError: DestinationDetailEvent()
    object SaveFavoriteDestinationSuccess: DestinationDetailEvent()
    object SaveFavoriteDestinationError: DestinationDetailEvent()
    object UndoFavoriteDestinationSuccess: DestinationDetailEvent()
    object UndoFavoriteDestinationError: DestinationDetailEvent()
}