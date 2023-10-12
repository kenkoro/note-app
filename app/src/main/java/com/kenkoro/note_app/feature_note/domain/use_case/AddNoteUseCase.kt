package com.kenkoro.note_app.feature_note.domain.use_case

import android.util.Log
import com.kenkoro.note_app.feature_note.domain.model.InvalidNoteException
import com.kenkoro.note_app.feature_note.domain.model.Note
import com.kenkoro.note_app.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNoteUseCase(
  private val noteRepository: NoteRepository
) {
  @Throws(InvalidNoteException::class)
  suspend operator fun invoke(note: Note) {
    if (note.title.isBlank()) {
      throw InvalidNoteException("The title of the note cannot be blank.")
    }
    if (note.content.isBlank()) {
      throw InvalidNoteException("The content of the note cannot be blank.")
    }

    noteRepository.upsertNote(note)
  }
}