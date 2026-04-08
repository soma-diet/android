package dev.skaba.soma.app.ui.components.forms

import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun FormCheckField(
    name: String,
    value: MutableState<Boolean>,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    FormInputField(
        name = name,
        modifier = modifier,
        error = null,
        required = false
    ) {
        // composition local provider - vypnuti minimalnich rozmeru checkboxu (kazi design)
        CompositionLocalProvider(
            LocalMinimumInteractiveComponentSize provides Dp.Unspecified
        ) {
            Checkbox(
                checked = value.value,
                onCheckedChange = { newValue ->
                    value.value = newValue
                    onValueChange(newValue)
                }
            )
        }
    }
}