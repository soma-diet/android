package dev.skaba.soma.app.ui.features.targets.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.forms.FormDecimalField
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsFormEvent
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsFormState

@Composable
fun TargetsForm(
  state: TargetsFormState,
  onEvent: (TargetsFormEvent) -> Unit,
  onSuccess: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val kcalInput = remember(state.kcal.value) { mutableStateOf(state.kcal.value) }
  val proteinInput = remember(state.protein.value) { mutableStateOf(state.protein.value) }
  val fatsInput = remember(state.fats.value) { mutableStateOf(state.fats.value) }
  val carbsInput = remember(state.carbs.value) { mutableStateOf(state.carbs.value) }
  val fiberInput = remember(state.fiber.value) { mutableStateOf(state.fiber.value) }
  val sodiumInput = remember(state.sodium.value) { mutableStateOf(state.sodium.value) }

  FormSection(
    title = "Set your targets",
  ) {
    FormDecimalField(
      name = "Energy (kcal)",
      value = kcalInput,
      error = state.kcal.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.KcalChanged(newValue)) },
      placeholder = "170",
    )
    FormDecimalField(
      name = "Fats",
      value = fatsInput,
      error = state.fats.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.FatsChanged(newValue)) },
      placeholder = "90",
    )
    FormDecimalField(
      name = "Carbohydrates",
      value = carbsInput,
      error = state.carbs.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.CarbsChanged(newValue)) },
      placeholder = "300",
    )
    FormDecimalField(
      name = "Protein",
      value = proteinInput,
      error = state.protein.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.ProteinChanged(newValue)) },
      placeholder = "150",
    )
    FormDecimalField(
      name = "Fiber",
      value = fiberInput,
      error = state.fiber.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.FiberChanged(newValue)) },
      placeholder = "15",
    )
    FormDecimalField(
      name = "Sodium",
      value = sodiumInput,
      error = state.sodium.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.SodiumChanged(newValue)) },
      placeholder = "2",
    )
    PrimaryButton(
      text = "Set targets",
      onClick = {
        onEvent(
          TargetsFormEvent.SaveTargets {
            onSuccess()
          },
        )
      },
      enabled = !state.isSaving,
      modifier = Modifier.fillMaxWidth(),
    )
  }
}