package dev.skaba.soma.app.ui.features.food.viewmodel

sealed interface FoodFormEvent {
  data class NameChanged(val name: String) : FoodFormEvent
  data class BrandChanged(val brand: String) : FoodFormEvent
  data class IsLiquidChanged(val isLiquid: Boolean) : FoodFormEvent
  data class ImageChanged(val imageUri: String?) : FoodFormEvent
  data class KcalChanged(val kcal: Float?) : FoodFormEvent
  data class CarbsChanged(val carbs: Float?) : FoodFormEvent
  data class ProteinChanged(val protein: Float?) : FoodFormEvent
  data class FatsChanged(val fats: Float?) : FoodFormEvent
  data class FiberChanged(val fiber: Float?) : FoodFormEvent
  data class SodiumChanged(val sodium: Float?) : FoodFormEvent

  object AddServing : FoodFormEvent
  data class ServingNameChanged(val servingId: String, val newName: String) : FoodFormEvent
  data class ServingSizeChanged(val servingId: String, val newSize: Float?) : FoodFormEvent
  data class RemoveServing(val servingId: String) : FoodFormEvent

  data class SaveFood(val onSuccess: () -> Unit) : FoodFormEvent
}