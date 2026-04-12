package dev.skaba.soma.app.ui.features.entry.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.R
import dev.skaba.soma.app.domain.food.Serving
import dev.skaba.soma.app.ui.components.boxes.ImageBox
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.forms.FormDecimalField
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.components.forms.FormSelectField
import dev.skaba.soma.app.ui.components.forms.SelectFieldOption
import dev.skaba.soma.app.ui.features.entry.viewmodel.EntryFormEvent
import dev.skaba.soma.app.ui.features.entry.viewmodel.EntryFormState

@Composable
fun LogEntryForm(
  state: EntryFormState,
  onEvent: (EntryFormEvent) -> Unit,
  onSuccess: () -> Unit,
  modifier: Modifier = Modifier,
) {
  fun servingToOption(serving: Serving): SelectFieldOption {
    return SelectFieldOption(serving.id, serving.name)
  }

  val food = state.food
  val defaultServing = SelectFieldOption(null, "g")
  val servingOptions =
    listOf(defaultServing) + (food?.servings?.map(::servingToOption) ?: emptyList())
  val selectedServing =
    if (state.selectedServing.value != null) servingToOption(state.selectedServing.value) else defaultServing

  FormSection(
    title = food?.name ?: "",
    modifier = modifier,
    verticalSpacing = 12.dp,
  ) {
    ImageBox(
      imageModel = food?.localImageUri ?: food?.remoteImageUrl,
      modifier = Modifier.height(224.dp),
    )

    FormSelectField(
      name = stringResource(R.string.label_serving),
      options = servingOptions,
      value = selectedServing,
      onValueChange = { newServing ->
        val serving =
          if (newServing.id != null) food?.servings?.find { it.id == newServing.id } else null
        onEvent(EntryFormEvent.ServingChanged(serving)) // null je default serving
      },
    )

    FormDecimalField(
      name = stringResource(R.string.label_quantity),
      placeholder = "1.0",
      value = state.quantity.value,
      error = state.quantity.error,
      onValueChange = { onEvent(EntryFormEvent.QuantityChanged(it)) },
    )

    PrimaryButton(
      text = if (state.isEditMode) stringResource(R.string.label_update) else stringResource(R.string.label_log),
      onClick = { onEvent(EntryFormEvent.SaveEntry(onSuccess)) },
      enabled = !state.isSaving && !state.isLoading,
      modifier = Modifier.fillMaxWidth(),
    )
  }
}
