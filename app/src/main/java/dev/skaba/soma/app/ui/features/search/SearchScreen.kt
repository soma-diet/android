package dev.skaba.soma.app.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.sample.FoodPreviewData
import dev.skaba.soma.app.ui.components.list.SomaItemList
import dev.skaba.soma.app.ui.components.list.SomaItemListEntryData
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.search.components.SearchField
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchEvent
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchState
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchViewModel
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun SearchScreen(
  searchViewModel: SearchViewModel,
) {
  val state by searchViewModel.state.collectAsState()
  SearchScreenContent(
    state = state,
    onEvent = { event -> searchViewModel.onEvent(event) }
  )
}

@Composable
fun SearchScreenContent(
  state: SearchState,
  onEvent: (SearchEvent) -> Unit,
  ) {
  val selectedFilterInput = remember(state.filter) { mutableStateOf(state.filter) }

  val spacing = 16.dp
  Scaffold(
    topBar = {
      SomaTextOnlyAppBar(
        text = "Food search"
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .padding(bottom = spacing)
    ) {
      SearchField(
        selectedFilter = selectedFilterInput,
        onQueryChanged = { newQuery -> onEvent(SearchEvent.QueryChanged(newQuery)) },
        onFilterChanged = { newFilter -> onEvent(SearchEvent.FilterChanged(newFilter)) },
        modifier = Modifier.padding(spacing)
      )
      SomaItemList(
        items = state.foods.map { food ->
          SomaItemListEntryData(
            name = food.name,
            subtext = food.brand,
            sidetext = "${food.macronutrients.kcal} kcal",
            onDelete = { onEvent(SearchEvent.DeleteFood(food.id)) },
            onEdit = { onEvent(SearchEvent.EditFood(food.id)) }
          )
        }
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
      onEvent = {}
    )
  }
}