package dev.skaba.soma.app.ui.components.forms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skaba.soma.app.ui.theme.SOMATheme

@Composable
fun FormSelectField(
  name: String,
  options: List<String>,
  value: MutableState<String>,
  modifier: Modifier = Modifier,
  placeholder: String? = null,
  required: Boolean = true,
  onValueChange: (String) -> Unit = {},
) {
  var expanded by remember { mutableStateOf(false) }

  FormInputField(
    name = name,
    modifier = modifier,
    required = required,
  ) {
    Column(
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.End,
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
          .clickable { expanded = !expanded }
          .padding(vertical = 4.dp),
      ) {
        Text(
          text = if (value.value != "") value.value else placeholder ?: "",
          style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.onSurface,
          textAlign = TextAlign.End,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
          imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
          contentDescription = "Select option",
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(20.dp),
        )
      }

      DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.small,
      ) {
        options.forEach { option ->
          DropdownMenuItem(
            text = {
              Text(
                text = option,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
              )
            },
            onClick = {
              value.value = option
              onValueChange(option)
              expanded = false
            },
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun FormSelectFieldPreview() {
  SOMATheme {
    FormSection(
      title = "test section",
    ) {
      FormSelectField(
        name = "testovaci pole",
        options = listOf("option 1", "option 2"),
        value = remember { mutableStateOf("option 1") },
      )
      Spacer(Modifier.height(300.dp))
    }
  }
}