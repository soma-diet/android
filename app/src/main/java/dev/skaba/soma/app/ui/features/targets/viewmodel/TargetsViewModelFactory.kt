package dev.skaba.soma.app.ui.features.targets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dev.skaba.soma.app.domain.targets.TargetsRepository

class TargetsViewModelFactory(
  private val targetsRepository: TargetsRepository,
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
    if (modelClass.isAssignableFrom(TargetsViewModel::class.java)) {
      return TargetsViewModel(targetsRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel")
  }
}