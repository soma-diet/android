package dev.skaba.soma.app.ui.features.targets.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.skaba.soma.app.ui.components.forms.FormNumberField
import dev.skaba.soma.app.ui.components.forms.FormSection

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