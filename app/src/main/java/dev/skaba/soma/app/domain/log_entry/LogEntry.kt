package dev.skaba.soma.app.domain.log_entry

import dev.skaba.soma.app.domain.food.Food
import dev.skaba.soma.app.domain.food.Macronutrients
import dev.skaba.soma.app.domain.food.Serving

data class LogEntry(
  val id: String,
  val timestamp: Long, // epoch milliseconds

  val food: Food,
  val serving: Serving?, // null = 1g default
  val quantity: Float,
) {
  val totalMacronutrients: Macronutrients
    get() {
      val servingSize = serving?.size ?: 1.0f
      val multiplier = (servingSize * quantity) / 100f

      return Macronutrients(
        kcal = food.macronutrients.kcal * multiplier,
        protein = food.macronutrients.protein * multiplier,
        carbs = food.macronutrients.carbs * multiplier,
        fats = food.macronutrients.fats * multiplier,
      )
    }
}
