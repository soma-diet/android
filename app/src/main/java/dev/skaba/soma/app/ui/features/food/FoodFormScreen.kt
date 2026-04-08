package dev.skaba.soma.app.ui.features.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.domain.model.Serving
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.buttons.SecondaryButton
import dev.skaba.soma.app.ui.components.forms.FormCheckField
import dev.skaba.soma.app.ui.components.forms.FormImageUpload
import dev.skaba.soma.app.ui.components.forms.FormNumberField
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.components.forms.FormTextField
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.theme.SOMATheme
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodFormScreen() {
  val spacing = 16.dp
  val scrollState = rememberScrollState()

  Scaffold(
    topBar = {
      SomaTextOnlyAppBar("New Food")
    }
  ) { paddingValues ->
    Column(
      verticalArrangement = Arrangement.spacedBy(spacing),
      modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = spacing)
        .fillMaxWidth()
        .verticalScroll(scrollState)
    ) {
      Spacer(modifier = Modifier.height(0.dp))
      FoodForm()
      Spacer(modifier = Modifier.height(0.dp))
    }
  }
}

@Composable
fun FoodForm(modifier: Modifier = Modifier) {
  FoodDetailsSection()
  FoodServingsSection()
  FoodNutrientsSection()
  PrimaryButton(text="Add", onClick = {}, modifier = Modifier.fillMaxWidth())
}

@Composable
fun FoodDetailsSection(modifier: Modifier = Modifier) {
  val foodName = remember { mutableStateOf("") }
  val brand = remember { mutableStateOf("") }
  val isLiquid = remember { mutableStateOf(false) }

  FormSection(
    title = "Food Details",
  ) {
    var selectedImageUri = null
    //var backendImageUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
    var backendImageUrl = null
    FormImageUpload(
      imageModel = selectedImageUri ?: backendImageUrl,
      onClearImage = {
        selectedImageUri = null
        // backendImageUrl = null
      },
      modifier = Modifier.height(150.dp)
    )
    FormTextField(
      name = "Food Name", value = foodName, placeholder = "Apple", required = true
    ) { value ->
      //   handle vlaue
    }
    FormTextField(
      name = "Brand",
      value = brand,
      placeholder = "Walmart",
      error = "Invalid characters",
      required = false
    ) { value ->
      //   handle vlaue
    }
    FormCheckField(
      name = "Is a liquid",
      value = isLiquid,
      onValueChange = {
        // handle value
      }
    )
  }
}

@Composable
fun FoodServingsSection(modifier: Modifier = Modifier) {
  val servings: MutableList<Serving> = remember {
    mutableStateListOf(
      Serving("serving_id1", "Sample name", 50.3f),
      Serving("serving_id2", "Sample name", 50.3f)
    )
  };
  FormSection(
    title = "Servings"
  ) {
    servings.forEachIndexed { index, serving ->
      key(serving.id) {
        ServingEditor(
          serving = serving,
          onServingRemoved = {
            servings.removeAt(index)
          }
        )
      }
    }
    SecondaryButton(
      text = "Add a serving",
      onClick = {
        servings.add(
          Serving(
            id = UUID.randomUUID().toString(),
            name = "",
            size = 0f,
          )
        )
      },
      modifier = Modifier.fillMaxWidth()
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
  val sizeInput = remember { mutableStateOf<Int?>(null) }

  Row(
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    IconButton(
      onClick = onServingRemoved,
      modifier = Modifier.padding(0.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Clear,
        contentDescription = "Delete serving"
      )
    }
  }
  FormTextField(
    name = "Serving name",
    value = nameInput,
    placeholder = "Piece",
  ) { }
  FormNumberField(
    name = "Size (g)",
    value = sizeInput,
    placeholder = "15 g"
  ) { }
}

@Composable
fun FoodNutrientsSection(modifier: Modifier = Modifier) {
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
      name = "Energy (kcal)",
      value = kcal,
      placeholder = "150 kcal",
      error = null,
      required = true
    ) { }
    FormNumberField(
      name = "Fats",
      value = fats,
      placeholder = "10 g",
      error = null,
      required = true
    ) { }
    FormNumberField(
      name = "Carbohydrates",
      value = carbs,
      placeholder = "67 g",
      error = null,
      required = true
    ) { }
    FormNumberField(
      name = "Protein",
      value = protein,
      placeholder = "15 g",
      error = null,
      required = true
    ) { }

    // micronutrients
    FormNumberField(
      name = "Fiber",
      value = fiber,
      placeholder = "2 g",
      error = null,
      required = true
    ) { }
    FormNumberField(
      name = "Salt",
      value = sodium,
      placeholder = "0.5 g",
      error = null,
      required = true
    ) { }
  }
}

@Preview(showBackground = true)
@Composable
private fun FoodFormScreenPreview() {
  SOMATheme {
    FoodFormScreen()
  }
}