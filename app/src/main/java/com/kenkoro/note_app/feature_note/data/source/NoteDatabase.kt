package com.kenkoro.note_app.feature_note.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kenkoro.note_app.feature_note.domain.model.Note

@Database(
  entities = [Note::class],
  version = 1
)
abstract class NoteDatabase : RoomDatabase() {
  abstract val dao: NoteDao

  companion object {
    const val DATABASE_NAME = "notes_db"
  }
}