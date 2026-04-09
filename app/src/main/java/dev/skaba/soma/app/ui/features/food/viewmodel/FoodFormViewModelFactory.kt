package dev.skaba.soma.app.ui.features.food.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.util.ImageProcessor

class FoodFormViewModelFactory(
  private val foodRepository: FoodRepository,
  private val imageProcessor: ImageProcessor,
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(FoodFormViewModel::class.java)) {
      return FoodFormViewModel(foodRepository, imageProcessor) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}