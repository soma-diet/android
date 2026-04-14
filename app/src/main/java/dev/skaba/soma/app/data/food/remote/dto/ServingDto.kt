package dev.skaba.soma.app.data.food.remote.dto

import dev.skaba.soma.app.domain.food.Serving
import kotlinx.serialization.Serializable

@Serializable
data class ServingDto(
  val id: String,
  val name: String,
  val size: Float
)

fun ServingDto.toDomain(): Serving {
  return Serving(
    id = this.id,
    name = this.name,
    size = this.size
  )
}