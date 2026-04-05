package dev.skaba.soma.app.ui.features.log.components.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.skaba.soma.app.R
import dev.skaba.soma.app.ui.components.scaffold.SomaAppBar
import dev.skaba.soma.app.ui.theme.SOMATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreenTopBar(
  onDayBackwards: () -> Unit,
  onDayForwards: () -> Unit,
) {
  SomaAppBar(
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    ChangeDayButton(
      icon = R.drawable.arrow_back,
      description = "Go back a day",
      onClick = onDayBackwards,
    )
    Text("Today", style = MaterialTheme.typography.headlineMedium)
    ChangeDayButton(
      icon = R.drawable.arrow_forward,
      description = "Go forward a day",
      onClick = onDayForwards
    )
  }
}

@Composable
private fun ChangeDayButton(
  icon: Int,
  description: String,
  onClick: () -> Unit,
) {
  IconButton(
    onClick = onClick
  ) {
    Icon(
      painter = painterResource(icon),
      contentDescription = description
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun LogScreenTopBarPreview() {
  SOMATheme {
    LogScreenTopBar(
      onDayBackwards = {},
      onDayForwards = {}
    )
  }
}