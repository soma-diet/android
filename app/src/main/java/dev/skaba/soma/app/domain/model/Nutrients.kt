package dev.skaba.soma.app.domain.model

data class Macronutrients(
  val kcal: Float,
  val protein: Float,
  val fats: Float,
  val carbs: Float
)

data class Micronutrients(
  val fiber: Float?,
  val sodium: Float?
)