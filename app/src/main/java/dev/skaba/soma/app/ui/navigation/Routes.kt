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
object LogEntryScreenRoute

@Serializable
object TargetsFormScreenRoute