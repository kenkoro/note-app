package com.kenkoro.note_app.feature_note.domain.util

sealed class NoteOrder(val sortType: SortType) {
  class Title(sortType: SortType) : NoteOrder(sortType)
  class Date(sortType: SortType) : NoteOrder(sortType)
  class Color(sortType: SortType) : NoteOrder(sortType)

  fun copy(sortType: SortType): NoteOrder {
    return when (this) {
      is Title -> Title(sortType)
      is Date -> Date(sortType)
      is Color -> Color(sortType)
    }
  }
}
