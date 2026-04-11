package dev.skaba.soma.app.ui.features.entry.viewmodel

import dev.skaba.soma.app.domain.food.Food
import dev.skaba.soma.app.domain.food.Serving
import dev.skaba.soma.app.ui.data.FormFieldState

data class EntryFormState(
  val food: Food? = null,
  val quantity: FormFieldState<Float?> = FormFieldState(1.0f),
  val selectedServing: FormFieldState<Serving?> = FormFieldState(null), // null = grams
  
  val isLoading: Boolean = false,
  val isSaving: Boolean = false,
  val isEditMode: Boolean = false
)
