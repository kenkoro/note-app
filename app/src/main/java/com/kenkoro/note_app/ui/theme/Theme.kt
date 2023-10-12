package com.kenkoro.note_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val darkColorScheme = darkColorScheme(
  primary = Color.White,
  background = DarkGray,
  onBackground = Color.White,
  surface = LightBlue,
  onSurface = DarkGray
)

@Composable
fun NoteTheme(
  content: @Composable () -> Unit
) {
  val colorScheme = darkColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    shapes = shapes,
    content = content
  )
}