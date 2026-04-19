package dev.skaba.soma.app.data.food.local

// jen vnorene -> nejsou realne tabulky

data class MacronutrientsEntity(
  val kcal: Float,
  val protein: Float,
  val fats: Float,
  val carbs: Float
)

data class MicronutrientsEntity(
  val fiber: Float?,
  val sodium: Float?
)