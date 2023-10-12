package com.kenkoro.note_app.feature_note.presentation.notes_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.kenkoro.note_app.feature_note.domain.model.Note

@Composable
fun NoteItem(
  note: Note,
  cornerRadius: Dp = 10.dp,
  cutCornerSize: Dp = 30.dp,
  onDeleteClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier) {
    Canvas(modifier = Modifier.matchParentSize()) {
      val clipPath = Path().apply{
        lineTo(size.width - cutCornerSize.toPx(), 0F)
        lineTo(size.width, cutCornerSize.toPx())
        lineTo(size.width, size.height)
        lineTo(0F, size.height)
        close()
      }

      clipPath(clipPath) {
        drawRoundRect(
          color = Color(note.color),
          size = size,
          cornerRadius = CornerRadius(cornerRadius.toPx())
        )
        drawRoundRect(
          color = Color(
            ColorUtils.blendARGB(note.color, 0x000000, 0.2F)
          ),
          topLeft = Offset(size.width - cutCornerSize.toPx(), -100F),
          size = Size(cutCornerSize.toPx() + 100F, cutCornerSize.toPx() + 100F),
          cornerRadius = CornerRadius(cornerRadius.toPx())
        )
      }
    }
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .padding(end = 32.dp)
    ) {
      Text(
        text = note.title,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
      Text(
        text = note.content,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        overflow = TextOverflow.Ellipsis
      )
    }
    IconButton(
      onClick = onDeleteClick,
      modifier = Modifier.align(Alignment.BottomEnd)
    ) {
      Icon(imageVector = Icons.Filled.AutoDelete, contentDescription = "Delete note")
    }
  }
}