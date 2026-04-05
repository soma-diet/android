package dev.skaba.soma.app.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun FormTextField(
  name: String,
  value: MutableState<String>,
  placeholder: String,
  modifier: Modifier = Modifier,
  error: String? = null,
  required: Boolean = true,
  onValueChange: (String) -> Unit,
) {
  FormInputField(
    name = name,
    modifier = modifier,
    error = error,
    required = required,
  ) {
    BasicTextField(
      value = value.value, onValueChange = { newValue ->
        value.value = newValue
        onValueChange(newValue)
      }, modifier = modifier, textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = if (error == null || error == "") MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
        textAlign = TextAlign.End
      ),
      // primary color indikator psani
      cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
      // nevykreslovat klasicky design
      decorationBox = { innerTextField ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(end = 0.dp),
          contentAlignment = Alignment.CenterEnd,
        ) {
          if (value.value.isEmpty()) {
            Text(
              text = placeholder,
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          innerTextField()
        }
      }
    )
  }
}

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

@Composable
fun FormInputField(
  name: String,
  modifier: Modifier = Modifier,
  error: String? = null,
  required: Boolean = true,
  inputField: @Composable () -> Unit,
) {
  Column(
    horizontalAlignment = Alignment.End,
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
      modifier = modifier.fillMaxWidth(),
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Text(text = name, style = MaterialTheme.typography.headlineSmall)
        if (required) {
          Text(
            text = "*",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
          )
        }
      }
      inputField()
    }
    if (error != null && error != "") {
      Text(
        text = error,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(top = 4.dp)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun FormInputFieldPreview() {
  SOMATheme {
    FormTextField(
      name = "Test input field",
      value = remember { mutableStateOf("") },
      placeholder = "Placeholder",
      onValueChange = { },
    )
  }
}