package dev.skaba.soma.app.ui.features.food.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.buttons.SecondaryButton
import dev.skaba.soma.app.ui.components.forms.FormCheckField
import dev.skaba.soma.app.ui.components.forms.FormDecimalField
import dev.skaba.soma.app.ui.components.forms.FormImageUpload
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.components.forms.FormTextField
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormEvent
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormState
import dev.skaba.soma.app.ui.features.food.viewmodel.ServingState

import androidx.compose.ui.res.stringResource
import dev.skaba.soma.app.R

@Composable
fun FoodDetailsSection(
  state: FoodFormState,
  onEvent: (FoodFormEvent) -> Unit,
  modifier: Modifier = Modifier,
) {
  // nastaveni photo picker launcheru
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia(),
  ) { uri ->
    if (uri != null) {
      onEvent(FoodFormEvent.ImageChanged(uri.toString()))
    }
  }

  FormSection(
    title = stringResource(R.string.title_food_details),
  ) {
    FormImageUpload(
      imageModel = state.localImageUri.value ?: state.remoteImageUrl,
      onClick = {
        photoPickerLauncher.launch(
          PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
        )
      },
      onClearImage = if (state.localImageUri.value != null) {
        { onEvent(FoodFormEvent.ImageChanged(null)) }
      } else null, // neukazovat delete moznost pokud je obrazek jen ze serveru
      error = state.localImageUri.error,
      modifier = Modifier.height(150.dp),
    )

    FormTextField(
      name = "Food Name",
      value = state.name.value,
      onValueChange = { newValue -> onEvent(FoodFormEvent.NameChanged(newValue)) },
      error = state.name.error,
      placeholder = "Apple",
    )

    FormTextField(
      name = "Brand",
      value = state.brand.value,
      onValueChange = { newValue -> onEvent(FoodFormEvent.BrandChanged(newValue)) },
      error = state.brand.error,
      placeholder = "Walmart",
      required = false,
    )

    FormCheckField(
      name = "Is a liquid",
      value = state.isLiquid.value,
      onValueChange = { newValue -> onEvent(FoodFormEvent.IsLiquidChanged(newValue)) },
    )
  }
}

@Composable
fun FoodServingsSection(
  state: FoodFormState,
  onEvent: (FoodFormEvent) -> Unit,
  modifier: Modifier = Modifier,
) {
  FormSection(
    title = stringResource(R.string.title_servings),
  ) {
    state.servings.forEach { servingState ->
      key(servingState.id) {
        ServingEditor(
          state = servingState,
          onEvent = onEvent,
        )
      }
    }
    SecondaryButton(
      text = "Add a serving",
      onClick = { onEvent(FoodFormEvent.AddServing) },
      modifier = Modifier.fillMaxWidth(),
    )
  }
}

@Composable
fun ServingEditor(
  state: ServingState,
  onEvent: (FoodFormEvent) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth(),
  ) {
    IconButton(
      onClick = { onEvent(FoodFormEvent.RemoveServing(state.id)) },
      modifier = Modifier.padding(0.dp),
    ) {
      Icon(
        imageVector = Icons.Default.Clear, contentDescription = "Delete serving",
      )
    }
  }

  FormTextField(
    name = "Serving name",
    value = state.name.value,
    onValueChange = { newValue -> onEvent(FoodFormEvent.ServingNameChanged(state.id, newValue)) },
    error = state.name.error,
    placeholder = "Piece",
  )

  FormDecimalField(
    name = "Size (g)",
    value = state.size.value,
    error = state.size.error,
    onValueChange = { newValue -> onEvent(FoodFormEvent.ServingSizeChanged(state.id, newValue)) },
    placeholder = "15 g",
  )
}

@Composable
fun FoodNutrientsSection(
  state: FoodFormState,
  onEvent: (FoodFormEvent) -> Unit,
  modifier: Modifier = Modifier,
) {
  FormSection(
    title = stringResource(R.string.title_nutritional_data),
  ) {
    Text(
      text = "Please enter values per 100g",
      style = MaterialTheme.typography.labelMedium,
    )

    // macronutrients
    FormDecimalField(
      name = stringResource(R.string.nutrient_kcal),
      value = state.kcal.value,
      onValueChange = { newValue -> onEvent(FoodFormEvent.KcalChanged(newValue)) },
      error = state.kcal.error,
      placeholder = "150 kcal",
    )
    FormDecimalField(
      name = stringResource(R.string.nutrient_fats),
      value = state.fats.value,
      onValueChange = { newValue -> onEvent(FoodFormEvent.FatsChanged(newValue)) },
      error = state.fats.error,
      placeholder = "10 g",
    )
    FormDecimalField(
      name = stringResource(R.string.nutrient_carbs),
      value = state.carbs.value,
      onValueChange = { newValue -> onEvent(FoodFormEvent.CarbsChanged(newValue)) },
      error = state.carbs.error,
      placeholder = "67 g",
    )
    FormDecimalField(
      name = stringResource(R.string.nutrient_protein),
      value = state.protein.value,
      onValueChange = { newValue -> onEvent(FoodFormEvent.ProteinChanged(newValue)) },
      error = state.protein.error,
      placeholder = "15 g",
    )

    // micronutrients
    FormDecimalField(
      name = stringResource(R.string.nutrient_fiber),
      value = state.fiber.value,
      onValueChange = { newValue -> onEvent(FoodFormEvent.FiberChanged(newValue)) },
      error = state.fiber.error,
      placeholder = "2 g",
      required = false,
    )
    FormDecimalField(
      name = stringResource(R.string.nutrient_sodium),
      value = state.sodium.value,
      onValueChange = { newValue -> onEvent(FoodFormEvent.SodiumChanged(newValue)) },
      error = state.sodium.error,
      placeholder = "0.5 g",
      required = false,
    )
  }
}