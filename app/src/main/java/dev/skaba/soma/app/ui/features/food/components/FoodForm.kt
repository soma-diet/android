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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.domain.food.Serving
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.buttons.SecondaryButton
import dev.skaba.soma.app.ui.components.forms.FormCheckField
import dev.skaba.soma.app.ui.components.forms.FormDecimalField
import dev.skaba.soma.app.ui.components.forms.FormImageUpload
import dev.skaba.soma.app.ui.components.forms.FormNumberField
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.components.forms.FormTextField
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormState
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormViewModel
import java.util.UUID

@Composable
fun FoodForm(
  viewModel: FoodFormViewModel,
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

  FoodServingsSection(viewModel = viewModel)
  FoodNutrientsSection(viewModel = viewModel)
  PrimaryButton(
    text = "Add",
    onClick = {
      viewModel.saveFood()
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
      imageModel = state.localImageUri.value ?: state.remoteImageUrl,
      onClick = {
        photoPickerLauncher.launch(
          PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
      },
      onClearImage =
        if (state.localImageUri.value != null) {
          { onImageChanged(null) }
        } else null, // neukazovat delete moznost pokud je obrazek jen ze serveru
      error = state.localImageUri.error,
      modifier = Modifier.height(150.dp)
    )

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
fun FoodServingsSection(viewModel: FoodFormViewModel, modifier: Modifier = Modifier) {
  val servings: MutableList<Serving> = remember {
    mutableStateListOf(
      Serving("serving_id1", "Sample name", 50.3f), Serving("serving_id2", "Sample name", 50.3f)
    )
  };
  FormSection(
    title = "Servings"
  ) {
    servings.forEachIndexed { index, serving ->
      key(serving.id) {
        ServingEditor(
          serving = serving, onServingRemoved = {
            servings.removeAt(index)
          })
      }
    }
    SecondaryButton(
      text = "Add a serving", onClick = {
        servings.add(
          Serving(
            id = UUID.randomUUID().toString(),
            name = "",
            size = 0f,
          )
        )
      }, modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
fun ServingEditor(
  serving: Serving,
  onServingRemoved: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val nameInput = remember { mutableStateOf(serving.name) }
  val sizeInput = remember { mutableStateOf<Float?>(null) }

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
    placeholder = "Piece",
  ) { }
  FormDecimalField(
    name = "Size (g)", value = sizeInput, placeholder = "15 g"
  ) { }
}

@Composable
fun FoodNutrientsSection(viewModel: FoodFormViewModel, modifier: Modifier = Modifier) {
  val kcal = remember { mutableStateOf<Int?>(null) }
  val protein = remember { mutableStateOf<Int?>(null) }
  val fats = remember { mutableStateOf<Int?>(null) }
  val carbs = remember { mutableStateOf<Int?>(null) }
  val fiber = remember { mutableStateOf<Int?>(null) }
  val sodium = remember { mutableStateOf<Int?>(null) }

  FormSection(
    title = "Nutritional data",
  ) {
    Text(text = "Please enter values per 100g", style = MaterialTheme.typography.labelMedium)

    // macronutrients
    FormNumberField(
      name = "Energy (kcal)", value = kcal, placeholder = "150 kcal", error = null, required = true
    ) { }
    FormNumberField(
      name = "Fats", value = fats, placeholder = "10 g", error = null, required = true
    ) { }
    FormNumberField(
      name = "Carbohydrates", value = carbs, placeholder = "67 g", error = null, required = true
    ) { }
    FormNumberField(
      name = "Protein", value = protein, placeholder = "15 g", error = null, required = true
    ) { }

    // micronutrients
    FormNumberField(
      name = "Fiber", value = fiber, placeholder = "2 g", error = null, required = true
    ) { }
    FormNumberField(
      name = "Salt", value = sodium, placeholder = "0.5 g", error = null, required = true
    ) { }
  }
}
