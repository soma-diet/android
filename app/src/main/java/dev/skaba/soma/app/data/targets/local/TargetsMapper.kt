package dev.skaba.soma.app.data.targets.local

import dev.skaba.soma.app.domain.targets.Targets

fun Targets.toEntity(): TargetsEntity {
  return TargetsEntity(
    kcal = this.kcal,
    carbs = this.carbs,
    protein = this.protein,
    fats = this.fats,
    fiber = this.fiber,
    sodium = this.sodium,
  )
}

fun TargetsEntity.toDomain(): Targets {
  return Targets(
    kcal = this.kcal,
    carbs = this.carbs,
    protein = this.protein,
    fats = this.fats,
    fiber = this.fiber,
    sodium = this.sodium,
  )
}