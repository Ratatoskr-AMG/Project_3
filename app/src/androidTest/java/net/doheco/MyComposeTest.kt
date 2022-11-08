package net.doheco

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.compose.ui.test.junit4.createComposeRule

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyComposeTest {

    @Test
    fun myComposeTest(){
        net.doheco.Questions.q1()
    }


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            ///MyAppTheme {
            //    MainScreen(uiState = fakeUiState, /*...*/)
            //}
        }

        //composeTestRule.onNodeWithText("Continue").performClick()

        //composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
}

