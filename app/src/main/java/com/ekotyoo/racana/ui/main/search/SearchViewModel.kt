package com.ekotyoo.racana.ui.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.repository.DestinationRepository
import com.ekotyoo.racana.ui.main.createtourplan.model.DestinationCategory
import com.ekotyoo.racana.ui.main.search.model.SearchEvent
import com.ekotyoo.racana.ui.main.search.model.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val destinationRepository: DestinationRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state

    private val _eventChannel = Channel<SearchEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onQueryChange(value: String) {
        _state.update { it.copy(query = value) }
    }

    fun onQueryClear() {
        _state.update { it.copy(query = "") }
    }

    fun onSearch() {
        if (_state.value.query.isNotBlank()) {
            search()
        }
    }

    fun onCategoryClick(category: DestinationCategory) {
        if (category == _state.value.selectedCategory) {
            _state.update { it.copy(selectedCategory = null) }
            return
        }
        _state.update { it.copy(selectedCategory = category) }
    }

    fun onSearchResultClick(id: Int) {
        viewModelScope.launch { _eventChannel.send(SearchEvent.NavigateToDetail(id)) }
    }

    private fun search() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = destinationRepository.getDestinations(_state.value.query)) {
                is Result.Success -> {
                    if (result.value.isNotEmpty()) {
                        _state.update { it.copy(searchResult = result.value) }
                    } else {
                        _eventChannel.send(SearchEvent.NotFound)
                    }
                }
                is Result.Error -> _eventChannel.send(SearchEvent.Error(result.message))
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}