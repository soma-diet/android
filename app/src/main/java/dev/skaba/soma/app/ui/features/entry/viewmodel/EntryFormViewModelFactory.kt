package dev.skaba.soma.app.ui.features.entry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.domain.log_entry.LogEntryRepository

class EntryFormViewModelFactory(
  private val logEntryRepository: LogEntryRepository,
  private val foodRepository: FoodRepository,
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
    if (modelClass.isAssignableFrom(EntryFormViewModel::class.java)) {
      val savedStateHandle = extras.createSavedStateHandle()
      return EntryFormViewModel(savedStateHandle, logEntryRepository, foodRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel")
  }
}
