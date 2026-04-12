package dev.skaba.soma.app.ui.components.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SomaItemListEntryData(
  val id: String,
  val name: String,
  val sidetext: String,
  val subtext: String? = null,
  val onClick: (() -> Unit)? = null,
  val onDelete: (() -> Unit)? = null,
  val onEdit: (() -> Unit)? = null,
)

@Composable
fun SomaItemList(
  items: List<SomaItemListEntryData>,
  modifier: Modifier = Modifier,
) {
  val spacing = 8.dp
  Surface(
    shape = MaterialTheme.shapes.medium,
    modifier = modifier.fillMaxSize(),
  ) {
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(spacing),
    ) {
      itemsIndexed(
        items = items,
        key = { _, item -> item.id }, // key aby pochopil pri updatovani ktery je ktery
      ) { index, item ->
        val topSpacing = if (index == 0) spacing else null
        val bottomSpacing = if (index == items.lastIndex) spacing else null
        SomaItemListEntry(
          name = item.name,
          subtext = item.subtext,
          sidetext = item.sidetext,
          onClick = item.onClick,
          onDeleteSwipe = item.onDelete,
          onEditSwipe = item.onEdit,
          topMargin = topSpacing,
          bottomMargin = bottomSpacing,
        )
      }
    }
  }
}

@Composable
private fun SomaItemListEntry(
  name: String,
  sidetext: String,
  subtext: String? = null,
  onClick: (() -> Unit)? = null,
  onDeleteSwipe: (() -> Unit)? = null,
  onEditSwipe: (() -> Unit)? = null,
  topMargin: Dp? = null,
  bottomMargin: Dp? = null,
) {
  val dismissState = rememberSwipeToDismissBoxState(
    positionalThreshold = { requiredDistance -> requiredDistance * 0.25f },
    confirmValueChange = { dismissValue ->
      when (dismissValue) {
        SwipeToDismissBoxValue.EndToStart -> {
          if (onEditSwipe != null) {
            onEditSwipe()
          }
          false // nechci aby zmizela
        }

        SwipeToDismissBoxValue.StartToEnd -> {
          if (onDeleteSwipe != null) {
            onDeleteSwipe()
          }
          true // chci aby zmizela
        }

        SwipeToDismissBoxValue.Settled -> false
      }
    },
  )

  SwipeToDismissBox(
    state = dismissState,
    backgroundContent = { DismissBackground(dismissState) },
    enableDismissFromStartToEnd = onDeleteSwipe != null,
    enableDismissFromEndToStart = onEditSwipe != null,
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        // podminena vlastjost modifieru
        .then(
          if (onClick != null) Modifier.clickable { onClick() } else Modifier,
        )
        .heightIn(min = 40.dp),
    ) {
      if (topMargin != null) {
        Spacer(Modifier.height(topMargin))
      }
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
      ) {

        Column {
          Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
          )
          if (subtext != null) {
            Text(
              text = subtext,
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
        Text(
          text = sidetext,
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.primary,
        )
      }
      if (bottomMargin != null) {
        Spacer(Modifier.height(bottomMargin))
      }
    }
  }
}

// pozadi swipe itemu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DismissBackground(dismissState: SwipeToDismissBoxState) {
  val deleteDirection = SwipeToDismissBoxValue.StartToEnd
  val editDirection = SwipeToDismissBoxValue.EndToStart

  // vybrat barvu pozadi podle toho na jakou stranu swipuje
  val color by animateColorAsState(
    when (dismissState.dismissDirection) {
      deleteDirection -> MaterialTheme.colorScheme.errorContainer // mazani
      editDirection -> MaterialTheme.colorScheme.tertiary // editace
      else -> Color.Transparent
    },
  )

  // vybrat zarovnani ikonky (nalevo potreba start, napravo end)
  val alignment = when (dismissState.dismissDirection) {
    deleteDirection -> Alignment.CenterStart
    editDirection -> Alignment.CenterEnd
    else -> Alignment.Center
  }

  // vybrat ikonku (nalevo Edit, napravo Delete)
  val icon = when (dismissState.dismissDirection) {
    deleteDirection -> Icons.Default.Delete
    editDirection -> Icons.Default.Edit
    else -> Icons.Default.Delete
  }

  val iconTint = when (dismissState.dismissDirection) {
    deleteDirection -> MaterialTheme.colorScheme.onError
    editDirection -> MaterialTheme.colorScheme.onTertiary // TODO onTeriary nemam definovanou
    else -> MaterialTheme.colorScheme.onSurface
  }

  val contentDescription = when (dismissState.dismissDirection) {
    deleteDirection -> "Delete"
    editDirection -> "Edit"
    else -> "Swipe action"
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color)
      .padding(horizontal = 20.dp),
    contentAlignment = alignment,
  ) {
    // Settled = neswipuje
    if (dismissState.dismissDirection != SwipeToDismissBoxValue.Settled) {
      Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        tint = iconTint,
      )
    }
  }
}