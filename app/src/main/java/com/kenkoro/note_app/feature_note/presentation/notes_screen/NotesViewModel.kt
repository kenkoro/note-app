package com.kenkoro.note_app.feature_note.presentation.notes_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenkoro.note_app.feature_note.domain.model.Note
import com.kenkoro.note_app.feature_note.domain.use_case.NoteUseCases
import com.kenkoro.note_app.feature_note.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
  private val noteUseCases: NoteUseCases
) : ViewModel() {
  private val _state = mutableStateOf(NotesState())
  val state: State<NotesState> = _state

  private var recentlyDeletedNote: Note? = null

  private var getNotesJob: Job? = null

  fun onEvent(event: NotesEvent) {
    when (event) {
      is NotesEvent.DeleteNote -> {
        viewModelScope.launch {
          val note = event.note
          noteUseCases.deleteNoteUseCase(note)
          recentlyDeletedNote = note
        }
      }
      is NotesEvent.Order -> {
        if (state.value.noteOrder::class == event.noteOrder::class &&
            state.value.noteOrder.sortType::class == event.noteOrder.sortType::class) {
          return
        }
        getNotes(event.noteOrder)
      }
      NotesEvent.RestoreNote -> {
        viewModelScope.launch {
          noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
          recentlyDeletedNote = null
        }
      }
      NotesEvent.ToggleOrderSection -> {
        _state.value = state.value.copy(
          isOrderSectionVisible = !state.value.isOrderSectionVisible
        )
      }
    }
  }

  private fun getNotes(noteOrder: NoteOrder) {
    getNotesJob?.cancel()
    getNotesJob = noteUseCases.getNotesUseCase(noteOrder)
      .onEach { notes ->
        _state.value = state.value.copy(
          notes = notes,
          noteOrder = noteOrder
        )
      }
      .launchIn(viewModelScope)
  }
}