package com.kenkoro.note_app.feature_note.domain.util

sealed class SortType {
  object Ascending : SortType()
  object Descending : SortType()
}
