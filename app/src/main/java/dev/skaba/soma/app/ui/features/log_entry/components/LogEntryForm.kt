package dev.skaba.soma.app.ui.features.log_entry.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.components.boxes.ImageBox
import dev.skaba.soma.app.ui.components.buttons.PrimaryButton
import dev.skaba.soma.app.ui.components.forms.FormNumberField
import dev.skaba.soma.app.ui.components.forms.FormSection
import dev.skaba.soma.app.ui.components.forms.FormSelectField

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