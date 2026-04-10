package dev.skaba.soma.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.skaba.soma.app.SomaApplication
import dev.skaba.soma.app.ui.features.food.FoodFormScreen
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormViewModel
import dev.skaba.soma.app.ui.features.food.viewmodel.FoodFormViewModelFactory
import dev.skaba.soma.app.ui.features.log.LogScreen
import dev.skaba.soma.app.ui.features.log_entry.LogEntryScreen
import dev.skaba.soma.app.ui.features.search.SearchScreen
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchViewModel
import dev.skaba.soma.app.ui.features.search.viewmodel.SearchViewModelFactory
import dev.skaba.soma.app.ui.features.targets.TargetsScreen
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsViewModel
import dev.skaba.soma.app.ui.features.targets.viewmodel.TargetsViewModelFactory
import dev.skaba.soma.app.ui.navigation.FoodFormScreenRoute
import dev.skaba.soma.app.ui.navigation.LogEntryScreenRoute
import dev.skaba.soma.app.ui.navigation.LogScreenRoute
import dev.skaba.soma.app.ui.navigation.SearchScreenRoute
import dev.skaba.soma.app.ui.navigation.SomaNavigationBar
import dev.skaba.soma.app.ui.navigation.TargetsFormScreenRoute
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun SomaApp() {
  val context = LocalContext.current
  val appContainer = (context.applicationContext as SomaApplication).container
  val navController = rememberNavController()

  Scaffold(
    bottomBar = { SomaNavigationBar(navController = navController) },
  ) { globalPadding -> // odsazeni od spodni listy

    NavHost(
      navController = navController,
      startDestination = LogScreenRoute,
      modifier = Modifier.padding(globalPadding),
    ) {
      composable<FoodFormScreenRoute> {
        val foodFormViewModel: FoodFormViewModel = viewModel(
          factory = FoodFormViewModelFactory(
            foodRepository = appContainer.foodRepository,
            imageProcessor = appContainer.imageProcessor,
          ),
        )
        FoodFormScreen(
          viewModel = foodFormViewModel,
          onFoodSaved = {
            navController.navigate(SearchScreenRoute)
          },
          navigateBack = { navController.popBackStack() },
        )
      }

      composable<LogScreenRoute> { LogScreen() }

      composable<SearchScreenRoute> {
        val searchViewModel: SearchViewModel = viewModel(
          factory = SearchViewModelFactory(
            foodRepository = appContainer.foodRepository,
          ),
        )
        SearchScreen(
          searchViewModel = searchViewModel,
          navigateToEditScreen = { foodId -> navController.navigate(FoodFormScreenRoute(foodId)) },
        )
      }

      composable<TargetsFormScreenRoute> {
        val targetsViewModel: TargetsViewModel = viewModel(
          factory = TargetsViewModelFactory(
            targetsRepository = appContainer.targetsRepository,
          ),
        )
        TargetsScreen(
          viewModel = targetsViewModel,
          navigateBack = { navController.popBackStack() },
        )
      }
      composable<LogEntryScreenRoute> { LogEntryScreen() }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SomaAppPreview() {
  SOMATheme {
    SomaApp()
  }
}