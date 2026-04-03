package dev.skaba.soma.app.ui.features.search.components.scaffold

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenTopBar(modifier: Modifier = Modifier) {
  TopAppBar(
    title = {
      Text("tady search screen top app bar")
    }
  )
}