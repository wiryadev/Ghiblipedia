package com.wiryadev.ghiblipedia.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.espresso.Espresso
import androidx.test.filters.LargeTest
import com.wiryadev.ghiblipedia.KoinTestRule
import com.wiryadev.ghiblipedia.MainActivity
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.data.FakeServiceDataSource
import com.wiryadev.ghiblipedia.di.databaseTestModule
import com.wiryadev.ghiblipedia.di.networkTestModule
import com.wiryadev.ghiblipedia.di.repositoryModule
import com.wiryadev.ghiblipedia.di.viewModelModule
import com.wiryadev.ghiblipedia.ui.components.FilmsListTestTag
import com.wiryadev.ghiblipedia.utils.onNodeWithContentDescriptionId
import com.wiryadev.ghiblipedia.utils.onNodeWithNavBarLabelId
import com.wiryadev.ghiblipedia.utils.onNodeWithTextId
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class GhibliAppNavigationKtTest {

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(
        modules = listOf(
            networkTestModule,
            databaseTestModule,
            repositoryModule,
            viewModelModule,
        )
    )

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")
    }

    @Test
    fun startDestination_shouldBeHome() {
        // "Home" tab should be selected when first opening the app
        composeTestRule.onNodeWithNavBarLabelId(R.string.home).assertIsSelected()
    }

    @Test
    fun bottomNavigation_shouldWorking() {
        composeTestRule.apply {
            // "Favorite" tab should be selected after clicked
            onNodeWithNavBarLabelId(R.string.favorite).performClick()
            onNodeWithNavBarLabelId(R.string.favorite).assertIsSelected()

            // "About" tab should be selected after clicked
            onNodeWithNavBarLabelId(R.string.about_page).performClick()
            onNodeWithNavBarLabelId(R.string.about_page).assertIsSelected()

            // Press system's back from any top level destination except "Home"
            // should navigate back to "Home"
            Espresso.pressBack()
            onNodeWithNavBarLabelId(R.string.home).assertIsSelected()
        }
    }

    @Test
    fun home_clickFirstItem_navigatesToDetailWithData() {
        composeTestRule.apply {
            // scroll to first item of the LazyList
            onNodeWithTag(FilmsListTestTag).performScrollToIndex(0)
            // Click on first item of the Lazy list
            onNodeWithText(FakeServiceDataSource.films[0].title).performClick()
            // Detail screen is opened, Favorite Button should be displayed
            onNodeWithContentDescription("Favorite Button").assertIsDisplayed()
            // Description of first item should be displayed
            onNodeWithText(FakeServiceDataSource.films[0].description).assertIsDisplayed()
        }
    }

    // home positive case
    @Test
    fun home_whenSearchItem_withValidQuery_shouldBeFound() {
        composeTestRule.apply {
            // SearchBar should be displayed
            // then input "Totoro" as search query
            onNodeWithTextId(R.string.search)
                .assertIsDisplayed()
                .performTextInput("Totoro")
            // Item with title "My Neighbor Totoro" should be found
            // therefore Empty Message should not exist
            onNodeWithText("My Neighbor Totoro").assertIsDisplayed()
            onNodeWithTextId(R.string.empty_search_result).assertDoesNotExist()
        }
    }

    // home negative case
    @Test
    fun home_whenSearchItem_withRandomInput_shouldNotFound() {
        composeTestRule.apply {
            // SearchBar should be displayed
            // then input random character as search query
            onNodeWithTextId(R.string.search)
                .assertIsDisplayed()
                .performTextInput("asdasdasdasd")
            // Empty Message should be displayed since search has no result
            onNodeWithTextId(R.string.empty_search_result).assertIsDisplayed()
        }
    }

    // Favorite negative case
    @Test
    fun favorite_whenFirstOpen_shouldEmpty() {
        composeTestRule.apply {
            // Click "Favorite" tab after opening the app
            onNodeWithNavBarLabelId(R.string.favorite).performClick()
            // Empty Message should be displayed
            // since no item has been added to favorite in first app launch
            onNodeWithTextId(R.string.empty_favorite).assertIsDisplayed()
        }
    }

    // Favorite positive case
    @Test
    fun favorite_afterAddFavorite_shouldNotEmpty() {
        composeTestRule.apply {
            // Click on first item of the Lazy list
            onNodeWithText(FakeServiceDataSource.films[0].title).performClick()
            // detail screen opened, click on the "Favorite" FAB
            onNodeWithContentDescription("Favorite Button").performClick()
            // back to top level destination using "Back" icon button
            onNodeWithContentDescriptionId(R.string.back).performClick()
            // Click "Favorite" tab after arrived at top level destination
            onNodeWithNavBarLabelId(R.string.favorite).performClick()

            // Empty Message should not exist
            // and LazyList should be displayed
            // since favorite is no longer empty
            onNodeWithTextId(R.string.empty_favorite).assertDoesNotExist()
            onNodeWithTag(FilmsListTestTag).assertIsDisplayed()
            onNodeWithText(FakeServiceDataSource.films[0].title).assertIsDisplayed()
        }
    }

    // about doesn't need negative case so only positive case
    @Test
    fun about_whenOpened_shouldShowNameAndEmail() {
        composeTestRule.apply {
            // Click "About" tab
            onNodeWithNavBarLabelId(R.string.about_page).performClick()
            // My name should be displayed
            onNodeWithText("Abrar Wiryawan", ignoreCase = true).assertIsDisplayed()
            // My email should be displayed too
            onNodeWithText("abrarwiryawan@gmail.com", ignoreCase = true).assertIsDisplayed()
        }
    }

}