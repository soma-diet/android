package dev.skaba.soma.app.ui.features.food.viewmodel

import dev.skaba.soma.app.ui.data.FormFieldState

data class FoodFormState(
  val name: FormFieldState<String> = FormFieldState(""),
  val brand: FormFieldState<String> = FormFieldState(""),
  val isLiquid: FormFieldState<Boolean> = FormFieldState(false),

  val localImageUri: FormFieldState<String?> = FormFieldState(null),
  val remoteImageUrl: String? = null,

  val kcal: FormFieldState<Float?> = FormFieldState(null),
  val carbs: FormFieldState<Float?> = FormFieldState(null),
  val protein: FormFieldState<Float?> = FormFieldState(null),
  val fats: FormFieldState<Float?> = FormFieldState(null),

  val fiber: FormFieldState<Float?> = FormFieldState(null),
  val sodium: FormFieldState<Float?> = FormFieldState(null),

  val isSaving: Boolean = false,
)

