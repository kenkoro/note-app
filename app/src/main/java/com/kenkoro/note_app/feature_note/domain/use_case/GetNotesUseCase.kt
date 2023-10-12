package com.kenkoro.note_app.feature_note.domain.use_case

import com.kenkoro.note_app.feature_note.domain.model.Note
import com.kenkoro.note_app.feature_note.domain.repository.NoteRepository
import com.kenkoro.note_app.feature_note.domain.util.NoteOrder
import com.kenkoro.note_app.feature_note.domain.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
  private val noteRepository: NoteRepository
) {
  operator fun invoke(
    noteOrder: NoteOrder = NoteOrder.Date(SortType.Descending)
  ): Flow<List<Note>> {
    return noteRepository.getNotes().map { notes ->
      when (noteOrder.sortType) {
        is SortType.Ascending -> {
          when (noteOrder) {
            is NoteOrder.Color -> notes.sortedBy { it.color }
            is NoteOrder.Date -> notes.sortedBy { it.timestamp }
            is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
          }
        }
        is SortType.Descending -> {
          when (noteOrder) {
            is NoteOrder.Color -> notes.sortedByDescending { it.color }
            is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
            is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
          }
        }
      }
    }
  }
}