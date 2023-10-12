package com.kenkoro.note_app.feature_note.presentation.add_edit_note_screen

data class NoteTextFieldState(
  val text: String = "",
  val hint: String = "",
  val isHintVisible: Boolean = true
)
