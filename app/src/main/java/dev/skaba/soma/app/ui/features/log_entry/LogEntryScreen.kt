package dev.skaba.soma.app.ui.features.log_entry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.boxes.ImageBox
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.forms.FormNumberField
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.components.forms.FormSelectField
import dev.skaba.soma.app.ui.components.scaffold.SomaTextOnlyAppBar
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun LogEntryScreen() {
    Scaffold(
        topBar = {
            SomaTextOnlyAppBar("Log food")
        }
    ) { paddingValues ->
        val spacing = 16.dp
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(spacing)
        ) {
            LogEntryForm()
        }
    }
}

@Composable
fun LogEntryForm(modifier: Modifier = Modifier) {
    val quantityInput = remember { mutableStateOf<Int?>(null)}
    val servingInput = remember { mutableStateOf<String>("g")}
    FormSection(
        title = "Food name", // TODO sem dat jmeno jidla
        modifier = modifier,
        verticalSpacing = 12.dp
    ) {
        ImageBox(
            imageModel = null,
            modifier = Modifier.height(144.dp)
        )
        FormSelectField(
            name = "Serving",
            options = listOf("g","test serving 1", "serving 2"),
            value = servingInput,
            error = null,
            onValueChange = { },
        )
        FormNumberField(
            name = "Quantity",
            placeholder = "3",
            value = quantityInput,
            onValueChange = {

            }
        )
        PrimaryButton(text="Log", onClick = {}, modifier=Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
private fun LogEntryScreenPreview() {
    SOMATheme {
        LogEntryScreen()
    }
}