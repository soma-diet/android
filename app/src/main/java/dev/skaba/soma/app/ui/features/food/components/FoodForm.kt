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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.buttons.SecondaryButton
import dev.skaba.soma.app.ui.components.forms.FormCheckField
import dev.skaba.soma.app.ui.components.forms.FormDecimalField
import dev.skaba.soma.app.ui.components.forms.FormImageUpload
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.components.forms.FormTextField
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormState
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormViewModel
import dev.skaba.soma.app.ui.features.food.viewmodel.ServingState

@Composable
fun FoodForm(
  viewModel: FoodFormViewModel,
  onFoodSaved: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val state by viewModel.state.collectAsState()

  FoodDetailsSection(
    state = state,
    onNameChanged = { viewModel.updateName(it) },
    onBrandChanged = { viewModel.updateBrand(it) },
    onIsLiquidChanged = { viewModel.updateIsLiquid(it) },
    onImageChanged = { uriString -> viewModel.updateImageUri(uriString) },
  )

  FoodServingsSection(
    state = state,
    onAddServing = { viewModel.addServing() },
    onRemoveServing = { viewModel.removeServing(it) },
    onNameChange = { servingId, newName -> viewModel.updateServingName(servingId, newName) },
    onSizeChange = { servingId, newSize -> viewModel.updateServingSize(servingId, newSize) },
  )

  FoodNutrientsSection(
    state = state,
    onKcalChanged = { viewModel.updateKcal(it) },
    onCarbsChanged = { viewModel.updateCarbs(it) },
    onProteinChanged = { viewModel.updateProtein(it) },
    onFatsChanged = { viewModel.updateFats(it) },
    onFiberChanged = { viewModel.updateFiber(it) },
    onSodiumChanged = { viewModel.updateSodium(it) },
  )

  PrimaryButton(
    text = "Add",
    onClick = {
      viewModel.saveFood {
        onFoodSaved()
      }
    },
    enabled = !state.isSaving,
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
fun FoodDetailsSection(
  state: FoodFormState,

  onNameChanged: (String) -> Unit,
  onBrandChanged: (String) -> Unit,
  onIsLiquidChanged: (Boolean) -> Unit,
  onImageChanged: (String?) -> Unit,

  modifier: Modifier = Modifier,
) {
  val nameInput = remember(state.name.value) { mutableStateOf(state.name.value) }
  val brandInput = remember(state.brand.value) { mutableStateOf(state.brand.value) }
  val isLiquidInput = remember(state.isLiquid.value) { mutableStateOf(state.isLiquid.value) }

  // nastaveni photo picker launcheru
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri ->
    if (uri != null) {
      onImageChanged(uri.toString())
    }
  }

  FormSection(
    title = "Food Details",
  ) {
    FormImageUpload(
      imageModel = state.localImageUri.value ?: state.remoteImageUrl, onClick = {
        photoPickerLauncher.launch(
          PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
      }, onClearImage = if (state.localImageUri.value != null) {
        { onImageChanged(null) }
      } else null, // neukazovat delete moznost pokud je obrazek jen ze serveru
      error = state.localImageUri.error, modifier = Modifier.height(150.dp))

    FormTextField(
      name = "Food Name",
      value = nameInput,
      onValueChange = onNameChanged,
      placeholder = "Apple",
      error = state.name.error,
    )

    FormTextField(
      name = "Brand",
      value = brandInput,
      onValueChange = onBrandChanged,
      placeholder = "Walmart",
      error = state.brand.error,
      required = false
    )

    FormCheckField(
      name = "Is a liquid",
      value = isLiquidInput,
      onValueChange = onIsLiquidChanged,
    )
  }
}

@Composable
fun FoodServingsSection(
  state: FoodFormState,
  onAddServing: () -> Unit,
  onRemoveServing: (String) -> Unit,
  onNameChange: (String, String) -> Unit,
  onSizeChange: (String, Float?) -> Unit,
  modifier: Modifier = Modifier,
) {
  FormSection(
    title = "Servings"
  ) {
    state.servings.forEach { servingState ->
      key(servingState.id) {
        ServingEditor(
          state = servingState,
          onNameChange = { onNameChange(servingState.id, it) },
          onSizeChange = { onSizeChange(servingState.id, it) },
          onServingRemoved = { onRemoveServing(servingState.id) })
      }
    }
    SecondaryButton(
      text = "Add a serving",
      onClick = onAddServing,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
fun ServingEditor(
  state: ServingState,
  onNameChange: (String) -> Unit,
  onSizeChange: (Float?) -> Unit,
  onServingRemoved: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val nameInput = remember(state.name.value) { mutableStateOf(state.name.value) }
  val sizeInput = remember(state.size.value) { mutableStateOf(state.size.value) }

  Row(
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    IconButton(
      onClick = onServingRemoved, modifier = Modifier.padding(0.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Clear, contentDescription = "Delete serving"
      )
    }
  }

  FormTextField(
    name = "Serving name",
    value = nameInput,
    onValueChange = onNameChange,
    error = state.name.error,
    placeholder = "Piece",
  )

  FormDecimalField(
    name = "Size (g)",
    value = sizeInput,
    onValueChange = onSizeChange,
    error = state.size.error,
    placeholder = "15 g",
  )
}

@Composable
fun FoodNutrientsSection(
  state: FoodFormState,
  onKcalChanged: (Float?) -> Unit,
  onCarbsChanged: (Float?) -> Unit,
  onProteinChanged: (Float?) -> Unit,
  onFatsChanged: (Float?) -> Unit,
  onFiberChanged: (Float?) -> Unit,
  onSodiumChanged: (Float?) -> Unit,
  modifier: Modifier = Modifier,
) {
  val kcalInput = remember(state.kcal.value) { mutableStateOf(state.kcal.value) }
  val proteinInput = remember(state.protein.value) { mutableStateOf(state.protein.value) }
  val fatsInput = remember(state.fats.value) { mutableStateOf(state.fats.value) }
  val carbsInput = remember(state.carbs.value) { mutableStateOf(state.carbs.value) }
  val fiberInput = remember(state.fiber.value) { mutableStateOf(state.fiber.value) }
  val sodiumInput = remember(state.sodium.value) { mutableStateOf(state.sodium.value) }

  FormSection(
    title = "Nutritional data",
  ) {
    Text(text = "Please enter values per 100g", style = MaterialTheme.typography.labelMedium)

    // macronutrients
    FormDecimalField(
      name = "Energy (kcal)",
      value = kcalInput,
      onValueChange = onKcalChanged,
      error = state.kcal.error,
      placeholder = "150 kcal",
    )
    FormDecimalField(
      name = "Fats",
      value = fatsInput,
      onValueChange = onFatsChanged,
      error = state.fats.error,
      placeholder = "10 g",
    )
    FormDecimalField(
      name = "Carbohydrates",
      value = carbsInput,
      onValueChange = onCarbsChanged,
      error = state.carbs.error,
      placeholder = "67 g",
    )
    FormDecimalField(
      name = "Protein",
      value = proteinInput,
      onValueChange = onProteinChanged,
      error = state.protein.error,
      placeholder = "15 g",
    )

    // micronutrients
    FormDecimalField(
      name = "Fiber",
      value = fiberInput,
      onValueChange = onFiberChanged,
      error = state.fiber.error,
      placeholder = "2 g",
      required = false
    )
    FormDecimalField(
      name = "Salt",
      value = sodiumInput,
      onValueChange = onSodiumChanged,
      error = state.sodium.error,
      placeholder = "0.5 g",
      required = false
    )
  }
}
