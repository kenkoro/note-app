package com.kenkoro.note_app.feature_note.presentation.notes_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kenkoro.note_app.core.util.TestTags
import com.kenkoro.note_app.feature_note.presentation.notes_screen.components.NoteItem
import com.kenkoro.note_app.feature_note.presentation.notes_screen.components.OrderSection
import com.kenkoro.note_app.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
  navController: NavController,
  viewModel: NotesViewModel = hiltViewModel()
) {
  val state = viewModel.state.value
  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = { navController.navigate(Screen.AddEditNoteScreen.route) },
        shape = CircleShape,
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.background
      ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add a new note")
      }
    },
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState)
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Your notes",
          style = MaterialTheme.typography.headlineLarge
        )
        IconButton(onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }) {
          Icon(imageVector = Icons.Filled.SortByAlpha, contentDescription = "Sort notes")
        }
      }
      AnimatedVisibility(
        visible = state.isOrderSectionVisible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
      ) {
        OrderSection(
          modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(TestTags.ORDER_SECTION),
          noteOrder = state.noteOrder,
          onOrderChange = {
            viewModel.onEvent(NotesEvent.Order(it))
          }
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
      LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (state.notes.isEmpty()) {
          item {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .size(500.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(
                text = "Nothing to seen here :(",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
              )
            }
          }
        }

        items(state.notes) { note ->
          NoteItem(
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                navController.navigate(
                  Screen.AddEditNoteScreen.route +
                      "?noteId=${note.id}&noteColor=${note.color}"
                )
              },
            note = note,
            onDeleteClick = {
              viewModel.onEvent(NotesEvent.DeleteNote(note))
              scope.launch {
                val result = snackbarHostState.showSnackbar(
                  message = "Note deleted",
                  actionLabel = "Undo"
                )
                if (result == SnackbarResult.ActionPerformed) {
                  viewModel.onEvent(NotesEvent.RestoreNote)
                }
              }
            }
          )
          Spacer(modifier = Modifier.height(16.dp))
        }
      }
    }
  }
}