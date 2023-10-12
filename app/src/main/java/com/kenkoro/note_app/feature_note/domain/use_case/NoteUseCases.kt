package com.kenkoro.note_app.feature_note.domain.use_case

data class NoteUseCases(
  val getNotesUseCase: GetNotesUseCase,
  val addNoteUseCase: AddNoteUseCase,
  val deleteNoteUseCase: DeleteNoteUseCase,
  val getNoteUseCase: GetNoteUseCase
)
