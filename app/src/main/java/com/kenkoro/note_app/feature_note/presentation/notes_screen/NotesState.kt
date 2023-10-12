package com.kenkoro.note_app.feature_note.presentation.notes_screen

import com.kenkoro.note_app.feature_note.domain.model.Note
import com.kenkoro.note_app.feature_note.domain.util.NoteOrder
import com.kenkoro.note_app.feature_note.domain.util.SortType

data class NotesState(
  val notes: List<Note> = emptyList(),
  val noteOrder: NoteOrder = NoteOrder.Date(SortType.Descending),
  val isOrderSectionVisible: Boolean = false
)
