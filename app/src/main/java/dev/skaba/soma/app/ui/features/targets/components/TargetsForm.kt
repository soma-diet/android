package dev.skaba.soma.app.ui.features.targets.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
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
  FormSection(
    title = "Set your targets",
  ) {
    FormDecimalField(
      name = "Energy (kcal)",
      value = state.kcal.value,
      error = state.kcal.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.KcalChanged(newValue)) },
      placeholder = "170",
    )
    FormDecimalField(
      name = "Fats",
      value = state.fats.value,
      error = state.fats.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.FatsChanged(newValue)) },
      placeholder = "90",
    )
    FormDecimalField(
      name = "Carbohydrates",
      value = state.carbs.value,
      error = state.carbs.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.CarbsChanged(newValue)) },
      placeholder = "300",
    )
    FormDecimalField(
      name = "Protein",
      value = state.protein.value,
      error = state.protein.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.ProteinChanged(newValue)) },
      placeholder = "150",
    )
    FormDecimalField(
      name = "Fiber",
      value = state.fiber.value,
      error = state.fiber.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.FiberChanged(newValue)) },
      placeholder = "15",
    )
    FormDecimalField(
      name = "Sodium",
      value = state.sodium.value,
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