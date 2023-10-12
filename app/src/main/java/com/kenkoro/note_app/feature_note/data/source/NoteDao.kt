package com.kenkoro.note_app.feature_note.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.kenkoro.note_app.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
  @Query("SELECT * FROM note")
  fun getNotes(): Flow<List<Note>>

  @Query("SELECT * FROM note WHERE id = :id")
  suspend fun getNoteById(id: Int): Note?

  @Upsert
  suspend fun upsertNote(note: Note)

  @Delete
  suspend fun deleteNote(note: Note)
}