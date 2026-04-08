package dev.skaba.soma.app.ui.features.targets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.forms.FormNumberField
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun TargetsScreen(modifier: Modifier = Modifier) {
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

@Composable
fun TargetsForm(
    modifier: Modifier = Modifier
) {
    // TODO potom zadat na aktualne zadane hodnoty
    val kcalInput = remember { mutableStateOf<Int?>(null) }
    val fatsInput = remember { mutableStateOf<Int?>(null) }
    val carbsInput = remember { mutableStateOf<Int?>(null) }
    val proteinInput = remember { mutableStateOf<Int?>(null) }
    val fiberInput = remember { mutableStateOf<Int?>(null) }
    val sodiumInput = remember { mutableStateOf<Int?>(null) }

    FormSection(
        title = "Set your targets"
    ) {
        FormNumberField(
            name = "Energy (kcal)",
            value = kcalInput,
            onValueChange = {},
            placeholder = "170",
        )
        FormNumberField(
            name = "Fats",
            value = fatsInput,
            onValueChange = {},
            placeholder = "90",
        )
        FormNumberField(
            name = "Carbohydrates",
            value = carbsInput,
            onValueChange = {},
            placeholder = "300",
        )
        FormNumberField(
            name = "Protein",
            value = proteinInput,
            onValueChange = {},
            placeholder = "150",
        )
        FormNumberField(
            name = "Fiber",
            value = fiberInput,
            onValueChange = {},
            placeholder = "15",
        )
        FormNumberField(
            name = "Sodium",
            value = sodiumInput,
            onValueChange = {},
            placeholder = "2",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TargetsScreenPreview() {
    SOMATheme {
        TargetsScreen()
    }
}