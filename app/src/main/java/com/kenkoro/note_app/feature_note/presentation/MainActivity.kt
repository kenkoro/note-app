package com.kenkoro.note_app.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kenkoro.note_app.feature_note.presentation.add_edit_note_screen.AddEditNoteScreen
import com.kenkoro.note_app.feature_note.presentation.notes_screen.NotesScreen
import com.kenkoro.note_app.feature_note.presentation.util.Screen
import com.kenkoro.note_app.ui.theme.NoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      NoteTheme {
        Surface(
          modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          val navController = rememberNavController()
          NavHost(navController = navController, startDestination = Screen.NotesScreen.route) {
            composable(route = Screen.NotesScreen.route) {
              NotesScreen(navController = navController)
            }

            composable(
              route = Screen.AddEditNoteScreen.route +
                  "?noteId={noteId}&noteColor={noteColor}",
              arguments = listOf(
                navArgument(name = "noteId") {
                  type = NavType.IntType
                  defaultValue = -1
                },
                navArgument(name = "noteColor") {
                  type = NavType.IntType
                  defaultValue = -1
                }
              )
            ) {
              val noteColor = it.arguments?.getInt("noteColor") ?: -1
              AddEditNoteScreen(navController = navController, noteColor = noteColor)
            }
          }
        }
      }
    }
  }
}