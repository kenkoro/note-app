package com.kenkoro.note_app.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kenkoro.note_app.core.util.TestTags
import com.kenkoro.note_app.di.AppModule
import com.kenkoro.note_app.feature_note.presentation.add_edit_note_screen.AddEditNoteScreen
import com.kenkoro.note_app.feature_note.presentation.notes_screen.NotesScreen
import com.kenkoro.note_app.feature_note.presentation.util.Screen
import com.kenkoro.note_app.ui.theme.NoteTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {
  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeRule = createAndroidComposeRule<MainActivity>()

  @Before
  fun setUp() {
    hiltRule.inject()
    composeRule.activity.setContent {
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

  private fun createNewNote() {
    composeRule
      .onNodeWithContentDescription("Add a new note")
      .performClick()
  }

  private fun saveNote() {
    composeRule
      .onNodeWithContentDescription("Save note")
      .performClick()
  }

  private fun inputNoteData(title: String, content: String) {
    inputNoteTitle(title)
    inputNoteContent(content)
  }

  private fun inputNoteTitle(title: String = "") {
    composeRule
      .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
      .performTextInput(title)
  }

  private fun inputNoteContent(content: String = "") {
    composeRule
      .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
      .performTextInput(content)
  }

  private fun assertThatNoteExist(title: String = "") {
    composeRule
      .onNodeWithText(title)
      .assertIsDisplayed()
  }

  private fun editNoteWithTitle(title: String = "") {
    composeRule
      .onNodeWithText(title)
      .performClick()
  }

  private fun assertNoteData(title: String = "", content: String = "") {
    composeRule
      .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
      .assertTextEquals(title)
    composeRule
      .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
      .assertTextEquals(content)
  }

  @Test
  fun shouldSaveNewNote_And_EditAfterwards() {
    createNewNote()

    inputNoteData("test-title", "test-content")
    saveNote()

    assertThatNoteExist("test-title")
    editNoteWithTitle("test-title")

    assertNoteData("test-title", "test-content")
    inputNoteTitle("test-title-2")
    saveNote()

    assertThatNoteExist("test-title-2")
  }
}