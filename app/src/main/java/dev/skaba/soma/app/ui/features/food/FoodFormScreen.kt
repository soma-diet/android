package dev.skaba.soma.app.ui.features.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.R
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.food.components.FoodDetailsSection
import dev.skaba.soma.app.ui.features.food.components.FoodNutrientsSection
import dev.skaba.soma.app.ui.features.food.components.FoodServingsSection
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormEvent
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormState
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormViewModel
import dev.skaba.soma.app.ui.theme.SOMATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodFormScreen(
  viewModel: FoodFormViewModel,
  onFoodSaved: () -> Unit,
  navigateBack: (() -> Unit)? = null,
) {
  val state by viewModel.state.collectAsState()
  FoodFormContent(
    state = state,
    onEvent = { event: FoodFormEvent -> viewModel.onEvent(event) },
    onFoodSaved = onFoodSaved,
    onNavigateBack = navigateBack,
  )
}

@Composable
fun FoodFormContent(
  state: FoodFormState,
  onEvent: (FoodFormEvent) -> Unit,
  onFoodSaved: () -> Unit,
  onNavigateBack: (() -> Unit)? = null,
) {
  val scrollState = rememberScrollState()
  val spacing = 16.dp
  Scaffold(
    topBar = {
      SomaTextOnlyAppBar(
        text = if (state.isEditMode) stringResource(R.string.title_edit_food) else stringResource(R.string.title_new_food),
        onNavigateBack = onNavigateBack,
      )
    },
    contentWindowInsets = WindowInsets(0.dp), // ignorovat inset od vnejsiho scaffoldu
  ) { paddingValues ->
    Column(
      verticalArrangement = Arrangement.spacedBy(spacing),
      modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = spacing)
        .fillMaxWidth()
        .verticalScroll(scrollState),
    ) {
      Spacer(modifier = Modifier.height(0.dp))

      FoodDetailsSection(
        state = state,
        onEvent = onEvent,
      )

      FoodServingsSection(
        state = state,
        onEvent = onEvent,
      )

      FoodNutrientsSection(
        state = state,
        onEvent = onEvent,
      )

      PrimaryButton(
        text = if (state.isEditMode) "Edit" else "Add",
        onClick = {
          onEvent(
            FoodFormEvent.SaveFood {
              onFoodSaved()
            },
          )
        },
        enabled = !state.isSaving, modifier = Modifier.fillMaxWidth(),
      )

      Spacer(modifier = Modifier.height(0.dp))
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun FoodFormContentPreview() {
  SOMATheme {
    FoodFormContent(
      state = FoodFormState(),
      onEvent = {},
      onFoodSaved = {},
    )
  }
}
