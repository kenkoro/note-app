package com.kenkoro.note_app.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kenkoro.note_app.ui.theme.BabyBlue
import com.kenkoro.note_app.ui.theme.LightGreen
import com.kenkoro.note_app.ui.theme.RedOrange
import com.kenkoro.note_app.ui.theme.RedPink
import com.kenkoro.note_app.ui.theme.Violet

@Entity
data class Note(
  @PrimaryKey val id: Int? = null,
  val title: String,
  val content: String,
  val timestamp: Long,
  val color: Int
) {
  companion object {
    val nodeColors = listOf(
      RedOrange,
      LightGreen,
      Violet,
      BabyBlue,
      RedPink
    )
  }
}

class InvalidNoteException(message: String) : Exception(message)