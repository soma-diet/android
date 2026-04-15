package dev.skaba.soma.app.data.food.remote.dto

import dev.skaba.soma.app.domain.food.Macronutrients
import dev.skaba.soma.app.domain.food.Micronutrients
import kotlinx.serialization.Serializable

@Serializable
data class MacronutrientsDto(
  val kcal: Float,
  val protein: Float,
  val carbs: Float,
  val fats: Float
)

@Serializable
data class MicronutrientsDto(
  val fiber: Float? = null,
  val sodium: Float? = null
)

fun MacronutrientsDto.toDomain(): Macronutrients {
  return Macronutrients(
    kcal = this.kcal,
    protein = this.protein,
    fats = this.fats,
    carbs = this.carbs
  )
}

fun MicronutrientsDto.toDomain(): Micronutrients {
  return Micronutrients(
    fiber = this.fiber,
    sodium = this.sodium
  )
}