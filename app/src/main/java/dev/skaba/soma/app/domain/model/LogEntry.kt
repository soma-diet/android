package dev.skaba.soma.app.domain.model

import java.time.ZonedDateTime

data class LogEntry(
  val id: String,
  val timestamp: ZonedDateTime,

  val food: Food,
  val serving: Serving,
  val quantity: Float,
) {
  val totalMacronutrients: Macronutrients
    get() {
      val multiplier = getMultiplier(this.serving.size, this.quantity)

      return Macronutrients(
        kcal = food.macronutrients.kcal * multiplier,
        protein = food.macronutrients.protein * multiplier,
        carbs = food.macronutrients.carbs * multiplier,
        fats = food.macronutrients.fats * multiplier
      )
    }

  val totalMicronutrients: Micronutrients?
    get() {
      val baseMicros = food.micronutrients ?: return null
      val multiplier = getMultiplier(this.serving.size, this.quantity)

      return Micronutrients(
        fiber = baseMicros.fiber?.times(multiplier),
        sodium = baseMicros.sodium?.times(multiplier)
      )
    }
}

private fun getMultiplier(servingSize: Float, quantity: Float): Float {
  return (servingSize * quantity) / 100f
}