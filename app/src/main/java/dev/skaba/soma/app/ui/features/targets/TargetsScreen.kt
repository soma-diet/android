package dev.skaba.soma.app.ui.features.targets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.features.targets.components.TargetsForm
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun TargetsScreen() {
    Scaffold(
        topBar = {
            SomaTextOnlyAppBar("Daily targets")
        }
    ) { paddingValues ->
        val spacing = 16.dp
        Column(
            modifier = Modifier.padding(paddingValues).padding(spacing)
        ) {
            TargetsForm()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TargetsScreenPreview() {
    SOMATheme {
        TargetsScreen()
    }
}