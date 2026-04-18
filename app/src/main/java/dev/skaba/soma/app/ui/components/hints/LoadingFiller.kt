package dev.skaba.soma.app.ui.components.hints

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.skaba.soma.app.ui.components.boxes.ContentSurface

@Composable
fun LoadingFiller(modifier: Modifier = Modifier) {
  ContentSurface {
    Box(
      modifier = modifier.fillMaxSize(),
    ) {
      CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
      )
    }
  }
}