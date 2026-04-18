package dev.skaba.soma.app.ui.components.boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ContentSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Surface(
    shape = MaterialTheme.shapes.medium,
    modifier = Modifier.fillMaxSize(),
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = modifier
        .fillMaxSize(),
    ) {
      content()
    }
  }
}