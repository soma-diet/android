package dev.skaba.soma.app.ui.features.targets.viewmodel

import dev.skaba.soma.app.ui.data.FormFieldState

data class TargetsFormState(
  val kcal: FormFieldState<Float?> = FormFieldState(null),
  val protein: FormFieldState<Float?> = FormFieldState(null),
  val carbs: FormFieldState<Float?> = FormFieldState(null),
  val fats: FormFieldState<Float?> = FormFieldState(null),
  val fiber: FormFieldState<Float?> = FormFieldState(null),
  val sodium: FormFieldState<Float?> = FormFieldState(null),
  val isSaving: Boolean = false,
)