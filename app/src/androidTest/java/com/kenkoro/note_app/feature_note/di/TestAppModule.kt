package com.kenkoro.note_app.feature_note.di

import android.app.Application
import androidx.room.Room
import com.kenkoro.note_app.feature_note.data.repository.NoteRepositoryImpl
import com.kenkoro.note_app.feature_note.data.source.NoteDatabase
import com.kenkoro.note_app.feature_note.domain.repository.NoteRepository
import com.kenkoro.note_app.feature_note.domain.use_case.AddNoteUseCase
import com.kenkoro.note_app.feature_note.domain.use_case.DeleteNoteUseCase
import com.kenkoro.note_app.feature_note.domain.use_case.GetNoteUseCase
import com.kenkoro.note_app.feature_note.domain.use_case.GetNotesUseCase
import com.kenkoro.note_app.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
  @Provides
  @Singleton
  fun provideNoteDatabase(app: Application): NoteDatabase {
    return Room.inMemoryDatabaseBuilder(
      app,
      NoteDatabase::class.java
    ).build()
  }

  @Provides
  @Singleton
  fun provideNoteRepository(db: NoteDatabase): NoteRepository {
    return NoteRepositoryImpl(db.dao)
  }

  @Provides
  @Singleton
  fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
    return NoteUseCases(
      getNotesUseCase = GetNotesUseCase(repository),
      addNoteUseCase = AddNoteUseCase(repository),
      deleteNoteUseCase = DeleteNoteUseCase(repository),
      getNoteUseCase = GetNoteUseCase(repository)
    )
  }
}