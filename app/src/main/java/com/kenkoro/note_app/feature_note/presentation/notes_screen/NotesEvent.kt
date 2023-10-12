package com.kenkoro.note_app.feature_note.presentation.notes_screen

import com.kenkoro.note_app.feature_note.domain.model.Note
import com.kenkoro.note_app.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
  data class Order(val noteOrder: NoteOrder): NotesEvent()
  data class DeleteNote(val note: Note) : NotesEvent()
  object RestoreNote : NotesEvent()
  object ToggleOrderSection : NotesEvent()
}
