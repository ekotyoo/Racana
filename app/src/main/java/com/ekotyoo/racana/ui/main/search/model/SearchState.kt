package com.ekotyoo.racana.ui.main.search.model

import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.ui.main.createtourplan.model.DestinationCategory

data class SearchState(
    val query: String = "",
    val selectedCategory: DestinationCategory? = null,
    val searchResult: List<TravelDestination> = emptyList(),
    val isLoading: Boolean = false
)
