package dev.skaba.soma.app.ui.features.log.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.skaba.soma.app.domain.log_entry.LogEntryRepository
import dev.skaba.soma.app.domain.targets.TargetsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class LogViewModel(
  private val logEntryRepository: LogEntryRepository,
  private val targetsRepository: TargetsRepository,
) : ViewModel() {
  private val _state = MutableStateFlow(LogState())
  val state: StateFlow<LogState> = _state.asStateFlow()

  // job aby se dala zastavit kdyz se zmeni den (jinak by nacital dal dny, na kterych uz UI neni)
  private var loadingEntriesJob: Job? = null

  init {
    // nacist targets
    viewModelScope.launch {
      targetsRepository.get().collectLatest { targets ->
        _state.update { it.copy(targets = targets) }
      }
    }

    loadEntriesForDay()
  }

  fun onEvent(event: LogEvent) {
    when (event) {
      LogEvent.MoveToNextDay -> changeDate(1)
      LogEvent.MoveToPreviousDay -> changeDate(-1)
      is LogEvent.DeleteEntry -> deleteEntry(event.entryId)
    }
  }

  private fun loadEntriesForDay() {
    loadingEntriesJob?.cancel() // zastavit nacitani predchozich entries pokud jsou '?.'
    loadingEntriesJob = viewModelScope.launch {
      _state.update { it.copy(isLoading = true) }
      logEntryRepository.getEntriesByDate(_state.value.date).collectLatest { entries ->
        _state.update { it.copy(entries = entries, isLoading = false) }
      }
    }
  }

  private fun changeDate(days: Int) {
    val calendar = Calendar.getInstance()
    calendar.time = _state.value.date
    calendar.add(Calendar.DAY_OF_YEAR, days) // posunout datum o X dnu
    _state.update { it.copy(date = calendar.time) }
    loadEntriesForDay()
  }

  private fun deleteEntry(entryId: String) {
    viewModelScope.launch {
      logEntryRepository.deleteById(entryId)
    }
  }
}
