package com.kenkoro.note_app.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.kenkoro.note_app.feature_note.data.repository.FakeNoteRepository
import com.kenkoro.note_app.feature_note.domain.model.Note
import com.kenkoro.note_app.feature_note.domain.repository.NoteRepository
import com.kenkoro.note_app.feature_note.domain.util.NoteOrder
import com.kenkoro.note_app.feature_note.domain.util.SortType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {
  private lateinit var getNotesUseCase: GetNotesUseCase
  private lateinit var fakeNoteRepository: NoteRepository

  @Before
  fun setUp() {
    fakeNoteRepository = FakeNoteRepository()
    getNotesUseCase = GetNotesUseCase(fakeNoteRepository)

    populateNotes()
  }

  private fun populateNotes() {
    val notesToInsert = mutableListOf<Note>()
    ('a'..'z').forEachIndexed { index, c ->
      notesToInsert.add(
        Note(
          title = c.toString(),
          content = c.toString(),
          timestamp = index.toLong(),
          color = index
        )
      )
    }

    notesToInsert.shuffle()
    runBlocking {
      notesToInsert.forEach { note ->
        fakeNoteRepository.upsertNote(note)
      }
    }
  }

  @Test
  fun `order notes by title ascending, should be in correct order`() = runBlocking {
    val notes = getNotesUseCase(NoteOrder.Title(SortType.Ascending)).first()

    for (idx in 0..notes.size - 2) {
      assertThat(notes[idx].title).isLessThan(notes[idx + 1].title)
    }
  }

  @Test
  fun `order notes by date ascending, should be in correct order`() = runBlocking {
    val notes = getNotesUseCase(NoteOrder.Date(SortType.Ascending)).first()

    for (idx in 0..notes.size - 2) {
      assertThat(notes[idx].timestamp).isLessThan(notes[idx + 1].timestamp)
    }
  }

  @Test
  fun `order notes by color ascending, should be in correct order`() = runBlocking {
    val notes = getNotesUseCase(NoteOrder.Color(SortType.Ascending)).first()

    for (idx in 0..notes.size - 2) {
      assertThat(notes[idx].color).isLessThan(notes[idx + 1].color)
    }
  }

  @Test
  fun `order notes by title descending, should be in correct order`() = runBlocking {
    val notes = getNotesUseCase(NoteOrder.Title(SortType.Descending)).first()

    for (idx in 0..notes.size - 2) {
      assertThat(notes[idx].title).isGreaterThan(notes[idx + 1].title)
    }
  }

  @Test
  fun `order notes by date descending, should be in correct order`() = runBlocking {
    val notes = getNotesUseCase(NoteOrder.Date(SortType.Descending)).first()

    for (idx in 0..notes.size - 2) {
      assertThat(notes[idx].timestamp).isGreaterThan(notes[idx + 1].timestamp)
    }
  }

  @Test
  fun `order notes by color descending, should be in correct order`() = runBlocking {
    val notes = getNotesUseCase(NoteOrder.Color(SortType.Descending)).first()

    for (idx in 0..notes.size - 2) {
      assertThat(notes[idx].color).isGreaterThan(notes[idx + 1].color)
    }
  }
}