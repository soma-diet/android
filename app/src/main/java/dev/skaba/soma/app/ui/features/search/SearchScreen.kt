package dev.skaba.soma.app.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.skaba.soma.app.ui.components.list.SomaItemList
import dev.skaba.soma.app.ui.components.list.SomaItemListEntryData
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.search.components.SearchField
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchViewModel
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun SearchScreen(
  searchViewModel: SearchViewModel,
) {
  val spacing = 16.dp


  val state = searchViewModel.state.collectAsState()

  val selectedFilterInput = remember(state.value.filter) { mutableStateOf(state.value.filter) }

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
        onQueryChanged = { newQuery ->
          // TODO search query
        },
        onFilterChanged = {
          // TODO filter query
        },
        modifier = Modifier.padding(spacing)
      )
      SomaItemList(
        items = state.value.foods.map { food ->
          SomaItemListEntryData(
            name = food.name,
            subtext = food.brand,
            sidetext = "${food.macronutrients.kcal} kcal",
            onDelete = { }, // TODO on delete
            onEdit = { } // TODO on edit
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
    SearchScreen(viewModel())
  }
}