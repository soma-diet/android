package dev.skaba.soma.app.data.food.remote.dto

import dev.skaba.soma.app.domain.food.Macronutrients
import dev.skaba.soma.app.domain.food.Micronutrients
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MacronutrientsDto(
  @SerialName("kcal") val kcal: Float,
  @SerialName("protein") val protein: Float,
  @SerialName("fats") val fats: Float,
  @SerialName("carbs") val carbs: Float
)

@Serializable
data class MicronutrientsDto(
  @SerialName("fiber") val fiber: Float? = null,
  @SerialName("sodium") val sodium: Float? = null
)

fun MacronutrientsDto.toDomainModel(): Macronutrients {
  return Macronutrients(
    kcal = this.kcal,
    protein = this.protein,
    fats = this.fats,
    carbs = this.carbs
  )
}

fun MicronutrientsDto.toDomainModel(): Micronutrients {
  return Micronutrients(
    fiber = this.fiber,
    sodium = this.sodium
  )
}