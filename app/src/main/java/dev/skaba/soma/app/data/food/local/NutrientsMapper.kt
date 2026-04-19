package dev.skaba.soma.app.data.food.local

import dev.skaba.soma.app.domain.food.Macronutrients
import dev.skaba.soma.app.domain.food.Micronutrients

fun Macronutrients.toEntity(): MacronutrientsEntity {
    return MacronutrientsEntity(
        kcal = kcal,
        protein = protein,
        fats = fats,
        carbs = carbs
    )
}

fun Micronutrients.toEntity(): MicronutrientsEntity {
    return MicronutrientsEntity(
        fiber = fiber,
        sodium = sodium
    )
}

fun MacronutrientsEntity.toDomain(): Macronutrients {
    return Macronutrients(
        kcal = kcal,
        protein = protein,
        fats = fats,
        carbs = carbs
    )
}

fun MicronutrientsEntity.toDomain(): Micronutrients {
    return Micronutrients(
        fiber = fiber,
        sodium = sodium
    )
}
