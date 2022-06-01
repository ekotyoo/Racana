package com.ekotyoo.racana.ui.main.search.model

sealed class SearchEvent {
    object NotFound : SearchEvent()
    data class Error(val message: String) : SearchEvent()
}
