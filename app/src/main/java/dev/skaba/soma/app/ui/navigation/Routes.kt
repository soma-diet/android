package dev.skaba.soma.app.ui.navigation

import kotlinx.serialization.Serializable

// routes definovane jako objekty
@Serializable
object SearchScreenRoute

@Serializable
object LogScreenRoute

@Serializable
data class FoodFormScreenRoute(val foodId: String? = null)

@Serializable
data class EntryFormScreenRoute(
  val foodId: String? = null,
  val entryId: String? = null
)

@Serializable
object TargetsFormScreenRoute