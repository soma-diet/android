package dev.skaba.soma.app.ui.features.search.model

enum class SearchFilter(
  val displayName: String,
) {
  ALL("All"),
  PRIVATE("Private"),
  PUBLIC("Public"),
}