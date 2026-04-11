package dev.skaba.soma.app.ui.features.log.viewmodel

import dev.skaba.soma.app.domain.log_entry.LogEntry
import dev.skaba.soma.app.domain.targets.Targets
import java.util.Date

data class LogState(
  val date: Date = Date(),
  val entries: List<LogEntry> = emptyList(),
  val targets: Targets = Targets(),
  val isLoading: Boolean = false,
)
