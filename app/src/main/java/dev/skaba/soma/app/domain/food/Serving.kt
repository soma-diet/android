package dev.skaba.soma.app.domain.food

import kotlinx.serialization.Serializable

@Serializable
data class Serving(
  val id: String,
  val name: String,
  val size: Float,
)