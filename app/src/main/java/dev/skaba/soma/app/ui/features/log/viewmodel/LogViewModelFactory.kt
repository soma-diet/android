package dev.skaba.soma.app.ui.features.log.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.skaba.soma.app.domain.log_entry.LogEntryRepository
import dev.skaba.soma.app.domain.targets.TargetsRepository

class LogViewModelFactory(
  private val logEntryRepository: LogEntryRepository,
  private val targetsRepository: TargetsRepository,
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return LogViewModel(logEntryRepository, targetsRepository) as T
  }
}
