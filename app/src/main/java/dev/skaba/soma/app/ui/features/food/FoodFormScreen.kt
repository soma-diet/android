package dev.skaba.soma.app.ui.features.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.forms.FormImageUpload
import dev.skaba.soma.app.ui.components.forms.FormInputField
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.theme.SOMATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodFormScreen() {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("New Food", style = MaterialTheme.typography.headlineMedium) })
    }) { paddingValues ->
    val scrollState = rememberScrollState()

    val spacing = 16.dp

    Column(
      verticalArrangement = Arrangement.spacedBy(spacing),
      modifier = Modifier
        .padding(paddingValues)
        .padding(spacing)
        .fillMaxWidth()
        .verticalScroll(scrollState)
    ) {
      FoodDetailsSection()
      FoodServingsSection()
      FoodNutrientsSection()
    }
  }
}

@Composable
fun FoodDetailsSection(modifier: Modifier = Modifier) {
  val foodName = remember { mutableStateOf("") }
  val brand = remember { mutableStateOf("") }

  FormSection(
    title = "Food Details",
  ) {
    var selectedImageUri = null
    // var backendImageUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
    var backendImageUrl = null
    FormImageUpload(
      imageModel = selectedImageUri ?: backendImageUrl,
      onClearImage = {
        selectedImageUri = null
        // backendImageUrl = null
      },
      modifier = Modifier.height(150.dp)
    )
    FormInputField(
      name = "Food Name", value = foodName, placeholder = "Apple", required = true
    ) { value ->
      //   handle vlaue
    }
    FormInputField(
      name = "Brand",
      value = brand,
      placeholder = "Walmart",
      error = "Invalid characters",
      required = false
    ) { value ->
      //   handle vlaue
    }
  }
}

@Composable
fun FoodServingsSection(modifier: Modifier = Modifier) {

}

@Composable
fun FoodNutrientsSection(modifier: Modifier = Modifier) {
  val kcal = remember { mutableStateOf("") }
  val protein = remember { mutableStateOf("") }
  val fats = remember { mutableStateOf("") }
  val carbs = remember { mutableStateOf("") }
  val fiber = remember { mutableStateOf("") }
  val sodium = remember { mutableStateOf("") }

  FormSection(
    title = "Nutritional data",
  ) {
    Text(text = "Please enter values per 100g", style = MaterialTheme.typography.labelMedium)

    // macronutrients
    FormInputField(
      name = "Energy (kcal)",
      value = kcal,
      placeholder = "150 kcal",
      error = null,
      required = true
    ) { }
    FormInputField(
      name = "Fats",
      value = fats,
      placeholder = "10 g",
      error = null,
      required = true
    ) { }
    FormInputField(
      name = "Carbohydrates",
      value = carbs,
      placeholder = "67 g",
      error = null,
      required = true
    ) { }
    FormInputField(
      name = "Protein",
      value = protein,
      placeholder = "15 g",
      error = null,
      required = true
    ) { }

    // micronutrients
    FormInputField(
      name = "Fiber",
      value = fiber,
      placeholder = "2 g",
      error = null,
      required = true
    ) { }
    FormInputField(
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