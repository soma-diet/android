package dev.skaba.soma.app.ui.features.entry.viewmodel

import dev.skaba.soma.app.domain.food.Serving

sealed class EntryFormEvent {
  data class QuantityChanged(val quantity: Float?) : EntryFormEvent()
  data class ServingChanged(val serving: Serving?) : EntryFormEvent()
  data class SaveEntry(val onSuccess: () -> Unit) : EntryFormEvent()
}
