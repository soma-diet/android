package dev.skaba.soma.app.ui.data

data class FormFieldState<T>(
  val value: T,
  val error: String? = null,
)

fun FormFieldState<String>.validateTextNotEmpty(errorMsg: String): FormFieldState<String> {
  return this.copy(error = if (this.value.isBlank()) errorMsg else null)
}

fun <T : Number> FormFieldState<T?>.validateNumberNotEmpty(errorMsg: String): FormFieldState<T?> {
  return this.copy(error = if (this.value == null) errorMsg else null)
}

fun <T : Number> FormFieldState<T?>.validateNumberNotNegative(errorMsg: String): FormFieldState<T?> {
  return this.copy(error = if (this.value != null && this.value.toFloat() < 0) errorMsg else null)
}