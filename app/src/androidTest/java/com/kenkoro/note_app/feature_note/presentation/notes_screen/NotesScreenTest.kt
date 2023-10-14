package com.kenkoro.note_app.feature_note.presentation.notes_screen

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kenkoro.note_app.core.util.TestTags
import com.kenkoro.note_app.di.AppModule
import com.kenkoro.note_app.feature_note.presentation.MainActivity
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
class NotesScreenTest {
  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeRule = createAndroidComposeRule<MainActivity>()

  @Before
  fun setUp() {
    hiltRule.inject()
    composeRule.activity.setContent {
      NoteTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.NotesScreen.route) {
          composable(Screen.NotesScreen.route) {
            NotesScreen(navController = navController)
          }
        }
      }
    }
  }

  private fun assertThatOrderSectionDoesNoteExist() {
    composeRule
      .onNodeWithTag(TestTags.ORDER_SECTION)
      .assertDoesNotExist()
  }

  private fun assertThatOrderSectionDoesExist() {
    composeRule
      .onNodeWithTag(TestTags.ORDER_SECTION)
      .assertIsDisplayed()
  }

  private fun clickToggleOrderSection() {
    composeRule
      .onNodeWithContentDescription("Sort notes")
      .performClick()
  }

  private fun clickToggleOrderSection_And_AssertThatOrderSectionAppears() {
    assertThatOrderSectionDoesExist()
    clickToggleOrderSection()
  }

  @Test
  fun shouldBeVisible_WhenClickToggleOrderSection() {
    assertThatOrderSectionDoesNoteExist()

    clickToggleOrderSection_And_AssertThatOrderSectionAppears()
  }
}