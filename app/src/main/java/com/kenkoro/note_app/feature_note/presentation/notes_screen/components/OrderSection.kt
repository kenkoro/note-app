package com.kenkoro.note_app.feature_note.presentation.notes_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kenkoro.note_app.feature_note.domain.util.NoteOrder
import com.kenkoro.note_app.feature_note.domain.util.SortType

@Composable
fun OrderSection(
  noteOrder: NoteOrder = NoteOrder.Date(SortType.Descending),
  onOrderChange: (NoteOrder) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Row(modifier = Modifier.fillMaxWidth()) {
      DefaultRadioButton(
        text = "Title",
        selected = noteOrder is NoteOrder.Title,
        onSelect = { onOrderChange(NoteOrder.Title(noteOrder.sortType)) }
      )
      Spacer(modifier = Modifier.width(8.dp))
      DefaultRadioButton(
        text = "Date",
        selected = noteOrder is NoteOrder.Date,
        onSelect = { onOrderChange(NoteOrder.Date(noteOrder.sortType)) }
      )
      Spacer(modifier = Modifier.width(8.dp))
      DefaultRadioButton(
        text = "Color",
        selected = noteOrder is NoteOrder.Color,
        onSelect = { onOrderChange(NoteOrder.Color(noteOrder.sortType)) }
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
      DefaultRadioButton(
        text = "Ascending",
        selected = noteOrder.sortType is SortType.Ascending,
        onSelect = { onOrderChange(noteOrder.copy(SortType.Ascending)) }
      )
      Spacer(modifier = Modifier.width(8.dp))
      DefaultRadioButton(
        text = "Descending",
        selected = noteOrder.sortType is SortType.Descending,
        onSelect = { onOrderChange(noteOrder.copy(SortType.Descending)) }
      )
    }
  }
}