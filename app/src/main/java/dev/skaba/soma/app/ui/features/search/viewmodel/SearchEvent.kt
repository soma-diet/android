package dev.skaba.soma.app.ui.features.search.viewmodel

import dev.skaba.soma.app.ui.features.search.model.SearchFilter

sealed interface SearchEvent {
  data class QueryChanged(val query: String) : SearchEvent
  data class FilterChanged(val filter: SearchFilter) : SearchEvent
  data class DeleteFood(val id: String) : SearchEvent
  data object LoadNextPage : SearchEvent
}
