package dev.skaba.soma.app.ui.features.log.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun ProgressOverview(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier.fillMaxWidth()
  ) {
    Column(
      horizontalAlignment = Alignment.Start,
      verticalArrangement = Arrangement.SpaceEvenly,
      modifier = Modifier
        .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
      TargetProgress(
        name = "energy",
        unit = "kcal",
        current = 356.5f,
        max = 2000.0f
      )
      TargetProgress(
        name = "protein",
        unit = "g",
        current = 67.5f,
        max = 150.0f
      )
    }
  }
}

@Composable
private fun TargetProgress(
  name: String,
  unit: String,
  current: Float,
  max: Float
) {
  val ratio = current / max

  Column(
    modifier = Modifier.padding(8.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(text = name)
      Text(text = "$current / ${max.toString().removeSuffix(".0")} $unit")
    }
    LinearProgressIndicator(
      progress = {
        // omezi ratio na range (0,1)
        ratio.coerceIn(0f, 1f)
      },
      modifier = Modifier
        .fillMaxWidth()
        .height(12.dp),
      drawStopIndicator = {},
      gapSize = 0.dp
    )

  }
}

@Preview(showBackground = true)
@Composable
private fun ProgressOverviewPreview() {
  SOMATheme {
    ProgressOverview()
  }
}