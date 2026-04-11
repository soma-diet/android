package dev.skaba.soma.app.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.R
import dev.skaba.soma.app.sample.FoodPreviewData
import dev.skaba.soma.app.ui.components.list.SomaItemList
import dev.skaba.soma.app.ui.components.list.SomaItemListEntryData
import dev.skaba.soma.app.ui.components.scaffold.SomaActionButton
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.search.components.SearchField
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchEvent
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchState
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchViewModel
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun SearchScreen(
  searchViewModel: SearchViewModel,
  navigateToNewFoodScreen: () -> Unit,
  navigateToEditScreen: (String?) -> Unit,
  onLogFood: (String) -> Unit,
) {
  val state by searchViewModel.state.collectAsState()
  SearchScreenContent(
    state = state,
    onEvent = { event -> searchViewModel.onEvent(event) },
    navigateToNewFoodScreen = navigateToNewFoodScreen,
    navigateToEditScreen = navigateToEditScreen,
    onLogFood = onLogFood,
  )
}

@Composable
fun SearchScreenContent(
  state: SearchState,
  onEvent: (SearchEvent) -> Unit,
  navigateToNewFoodScreen: () -> Unit,
  navigateToEditScreen: (String?) -> Unit,
  onLogFood: (String) -> Unit,
) {
  val selectedFilterInput = remember(state.filter) { mutableStateOf(state.filter) }

  val spacing = 16.dp
  Scaffold(
    topBar = {
      SomaTextOnlyAppBar(
        text = "Food search",
      )
    },
    floatingActionButton = {
      SomaActionButton(
        onClick = navigateToNewFoodScreen,
      ) {
        Icon(
          painter = painterResource(R.drawable.add_24px),
          contentDescription = "Add food",
          modifier = Modifier.size(24.dp),
        )
      }
    },
    contentWindowInsets = WindowInsets(0.dp), // ignorovat inset od vnejsiho scaffoldu
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .padding(bottom = spacing),
    ) {
      SearchField(
        selectedFilter = selectedFilterInput,
        onQueryChanged = { newQuery -> onEvent(SearchEvent.QueryChanged(newQuery)) },
        onFilterChanged = { newFilter -> onEvent(SearchEvent.FilterChanged(newFilter)) },
        modifier = Modifier.padding(spacing),
      )
      SomaItemList(
        items = state.foods.map { food ->
          val onDelete = if (food.isPrivate) {
            { onEvent(SearchEvent.DeleteFood(food.id)) }
          } else {
            null
          }
          val onEdit = if (food.isPrivate) {
            {
              // onEvent(SearchEvent.EditFood(food.id))
              navigateToEditScreen(food.id)
            }
          } else {
            null
          }

          SomaItemListEntryData(
            id = food.id,
            name = food.name,
            subtext = food.brand,
            sidetext = "${food.macronutrients.kcal} kcal",
            onClick = { onLogFood(food.id) },
            onDelete = onDelete,
            onEdit = onEdit,
          )
        },
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
  SOMATheme {
    SearchScreenContent(
      state = SearchState(
        query = "Test query",
        foods = FoodPreviewData.allSamples,
      ),
      onEvent = {},
      navigateToNewFoodScreen = {},
      navigateToEditScreen = {},
      onLogFood = {},
    )
  }
}