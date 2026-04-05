package dev.skaba.soma.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.skaba.soma.app.ui.features.food.FoodFormScreen
import dev.skaba.soma.app.ui.features.log.LogScreen
import dev.skaba.soma.app.ui.features.search.SearchScreen
import dev.skaba.soma.app.ui.navigation.FoodFormScreenRoute
import dev.skaba.soma.app.ui.navigation.LogScreenRoute
import dev.skaba.soma.app.ui.navigation.SearchScreenRoute
import dev.skaba.soma.app.ui.navigation.SomaNavigationBar
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun SomaApp() {
  val navController = rememberNavController()

  Scaffold(
    bottomBar = { SomaNavigationBar(navController = navController) }
  ) { globalPadding -> // odsazeni od spodni listy

    NavHost(
      navController = navController,
      startDestination = LogScreenRoute,
      modifier = Modifier.padding(globalPadding)
    ) {
      composable<LogScreenRoute> { LogScreen() }
      composable<FoodFormScreenRoute> { FoodFormScreen() }
      composable<SearchScreenRoute> { SearchScreen() }
    }
  }
}

@Preview(showBackground=true)
@Composable
private fun SomaAppPreview() {
  SOMATheme {
    SomaApp()
  }
}