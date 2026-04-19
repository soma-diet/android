package dev.skaba.soma.app.data.log_entry.local

import dev.skaba.soma.app.data.food.local.toDomain
import dev.skaba.soma.app.domain.log_entry.LogEntry

fun LogEntry.toEntity(): LogEntryEntity {
  return LogEntryEntity(
    id = this.id,
    timestamp = this.timestamp,
    foodId = this.food.id,
    servingId = this.serving?.id,
    quantity = this.quantity,
  )
}

fun LogEntryWithFood.toDomain(): LogEntry {
  val food = this.food.toDomain()
  val serving = if (this.entry.servingId != null) {
    food.servings.find { it.id == this.entry.servingId }
  } else {
    null
  }

  return LogEntry(
    id = this.entry.id,
    timestamp = this.entry.timestamp,
    food = food,
    serving = serving,
    quantity = this.entry.quantity,
  )
}
