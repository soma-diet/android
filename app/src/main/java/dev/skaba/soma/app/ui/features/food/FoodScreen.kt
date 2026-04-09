package dev.skaba.soma.app.ui.features.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.food.components.FoodForm
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormViewModel
import dev.skaba.soma.app.ui.theme.SOMATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(
  foodFormViewModel: FoodFormViewModel,
) {
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
      FoodForm(viewModel = foodFormViewModel)
      Spacer(modifier = Modifier.height(0.dp))
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun FoodScreenPreview() {
  SOMATheme {
    FoodScreen(viewModel())
  }
}