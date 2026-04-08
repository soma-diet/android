package dev.skaba.soma.app.data.food.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LogEntryResponseDto(
  val id: String,
  val timestamp: String, // java local datetime "2026-04-04T10:21:21.835Z"
  val item: FoodResponseDto,
  val servingId: String? = null,
  val servingName: String? = null,
  val servingSize: Float? = null,
  val quantity: Float
)

@Serializable
data class LogEntryRequestDto(
  val id: String,
  val timestamp: String, // java localdatetime "2026-04-04T10:21:21"
  val itemId: String,
  val servingId: String? = null,
  val quantity: Float
)