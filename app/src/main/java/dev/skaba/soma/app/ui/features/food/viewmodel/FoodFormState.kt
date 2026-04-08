package dev.skaba.soma.app.ui.features.food.viewmodel

data class FoodFormState(
  val name: String = "",
  val kcal: Int? = null,
  val carbs: Float? = null,
  val proteins: Float? = null,
  val fats: Float? = null,

  val isSaving: Boolean = false,
  val error: String? = null,
)