package dev.skaba.soma.app.ui.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.ui.features.search.model.SearchFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
  private val foodRepository: FoodRepository,
) : ViewModel() {
  private val _state = MutableStateFlow(SearchState())
  val state: StateFlow<SearchState> = _state.asStateFlow()

  init {
    search(_state.value.query, _state.value.filter)
  }

  fun onEvent(event: SearchEvent) {
    when (event) {
      is SearchEvent.QueryChanged -> {
        _state.value = _state.value.copy(query = event.query)
        search(event.query, _state.value.filter)
      }

      is SearchEvent.FilterChanged -> {
        _state.value = _state.value.copy(filter = event.filter)
        search(_state.value.query, event.filter)
      }

      is SearchEvent.DeleteFood -> {
        viewModelScope.launch {
          foodRepository.deleteById(event.id)
          search(_state.value.query, _state.value.filter) // aktulizovat data
        }
      }

      SearchEvent.LoadNextPage -> {
        if (!_state.value.isLastPage && !_state.value.isLoading) {
          search(_state.value.query, _state.value.filter, reset = false)
        }
      }
    }
  }

  private fun search(query: String, filter: SearchFilter, reset: Boolean = true) {
    viewModelScope.launch {
      val page = if (reset) 0 else _state.value.currentPage + 1

      _state.value = _state.value.copy(
        isLoading = true,
        currentPage = page,
        isLastPage = if (reset) false else _state.value.isLastPage,
        foods = if (reset) emptyList() else _state.value.foods,
      )

      val newFoods = foodRepository.searchByName(query, filter, page = page)

      _state.value = _state.value.copy(
        isLoading = false,
        isLastPage = newFoods.isEmpty(),
        foods = if (reset) newFoods else _state.value.foods + newFoods,
      )
    }
  }
}