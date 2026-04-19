package dev.skaba.soma.app.ui.features.log.viewmodel

sealed class LogEvent {
  object MoveToNextDay : LogEvent()
  object MoveToPreviousDay : LogEvent()
  data class DeleteEntry(val entryId: String) : LogEvent()
}