package dev.skaba.soma.app.ui.features.targets.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.forms.FormDecimalField
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsFormEvent
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsFormState

import androidx.compose.ui.res.stringResource
import dev.skaba.soma.app.R

@Composable
fun TargetsForm(
  state: TargetsFormState,
  onEvent: (TargetsFormEvent) -> Unit,
  onSuccess: () -> Unit,
  modifier: Modifier = Modifier,
) {
  FormSection(
    title = stringResource(R.string.title_set_targets),
  ) {
    FormDecimalField(
      name = stringResource(R.string.nutrient_kcal),
      value = state.kcal.value,
      error = state.kcal.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.KcalChanged(newValue)) },
      placeholder = "170",
      required = false,
    )
    FormDecimalField(
      name = stringResource(R.string.nutrient_fats),
      value = state.fats.value,
      error = state.fats.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.FatsChanged(newValue)) },
      placeholder = "90",
      required = false,
    )
    FormDecimalField(
      name = stringResource(R.string.nutrient_carbs),
      value = state.carbs.value,
      error = state.carbs.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.CarbsChanged(newValue)) },
      placeholder = "300",
      required = false,
    )
    FormDecimalField(
      name = stringResource(R.string.nutrient_protein),
      value = state.protein.value,
      error = state.protein.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.ProteinChanged(newValue)) },
      placeholder = "150",
      required = false,
    )
    FormDecimalField(
      name = stringResource(R.string.nutrient_fiber),
      value = state.fiber.value,
      error = state.fiber.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.FiberChanged(newValue)) },
      placeholder = "15",
      required = false,
    )
    FormDecimalField(
      name = stringResource(R.string.nutrient_sodium),
      value = state.sodium.value,
      error = state.sodium.error,
      onValueChange = { newValue -> onEvent(TargetsFormEvent.SodiumChanged(newValue)) },
      placeholder = "2",
      required = false,
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