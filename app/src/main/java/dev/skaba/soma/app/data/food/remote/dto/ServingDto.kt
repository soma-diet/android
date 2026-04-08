package dev.skaba.soma.app.data.food.remote.dto

import dev.skaba.soma.app.domain.food.Serving
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServingDto(
  @SerialName("id") val id: String,
  @SerialName("name") val name: String,
  @SerialName("size") val size: Float,
)

fun ServingDto.toDomainModel(): Serving {
  return Serving(
    id = this.id,
    name = this.name,
    size = this.size
  )
}