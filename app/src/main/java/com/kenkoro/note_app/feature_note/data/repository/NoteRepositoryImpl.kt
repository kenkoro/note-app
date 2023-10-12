package com.kenkoro.note_app.feature_note.data.repository

import android.util.Log
import com.kenkoro.note_app.feature_note.data.source.NoteDao
import com.kenkoro.note_app.feature_note.domain.model.Note
import com.kenkoro.note_app.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
  private val dao: NoteDao
) : NoteRepository {
  override fun getNotes(): Flow<List<Note>> {
    return dao.getNotes()
  }

  override suspend fun getNoteById(id: Int): Note? {
    return dao.getNoteById(id)
  }

  override suspend fun upsertNote(note: Note) {
    return dao.upsertNote(note)
  }

  override suspend fun deleteNote(note: Note) {
    return dao.deleteNote(note)
  }
}