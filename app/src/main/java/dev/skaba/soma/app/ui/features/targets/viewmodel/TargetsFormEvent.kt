package dev.skaba.soma.app.ui.features.targets.viewmodel

interface TargetsFormEvent {
  data class KcalChanged(val value: Float?) : TargetsFormEvent
  data class ProteinChanged(val value: Float?) : TargetsFormEvent
  data class CarbsChanged(val value: Float?) : TargetsFormEvent
  data class FatsChanged(val value: Float?) : TargetsFormEvent
  data class FiberChanged(val value: Float?) : TargetsFormEvent
  data class SodiumChanged(val value: Float?) : TargetsFormEvent
  data class SaveTargets(val onSuccess: () -> Unit) : TargetsFormEvent
}