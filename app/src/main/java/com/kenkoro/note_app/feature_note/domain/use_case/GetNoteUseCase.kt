package com.kenkoro.note_app.feature_note.domain.use_case

import com.kenkoro.note_app.feature_note.domain.model.Note
import com.kenkoro.note_app.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(
  private val noteRepository: NoteRepository
) {
  suspend operator fun invoke(id: Int): Note? {
    return noteRepository.getNoteById(id)
  }
}