package com.ekotyoo.racana.ui.main.search.model

import com.ekotyoo.racana.data.model.TravelDestination

data class SearchState(
    val query: String = "",
    val selectedCategory: Int? = null,
    val searchResult: List<TravelDestination> = emptyList(),
    val isLoading: Boolean = false
)
