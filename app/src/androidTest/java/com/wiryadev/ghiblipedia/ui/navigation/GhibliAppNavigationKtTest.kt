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
    fun verifyStartDestinationIsHome() {
        composeTestRule.onNodeWithNavBarLabelId(R.string.home).assertIsSelected()
    }

    @Test
    fun bottomNavigation_working() {
        composeTestRule.apply {
            onNodeWithNavBarLabelId(R.string.favorite).performClick()
            onNodeWithNavBarLabelId(R.string.favorite).assertIsSelected()
            onNodeWithNavBarLabelId(R.string.about_page).performClick()
            onNodeWithNavBarLabelId(R.string.about_page).assertIsSelected()
            Espresso.pressBack()
            onNodeWithNavBarLabelId(R.string.home).assertIsSelected()
        }
    }

    @Test
    fun home_clickFirstItem_navigatesToDetailWithData() {
        composeTestRule.apply {
            onNodeWithTag(FilmsListTestTag).performScrollToIndex(0)
            onNodeWithText(FakeServiceDataSource.films[0].title).performClick()
            onNodeWithContentDescription("Favorite Button").assertIsDisplayed()
            onNodeWithText(FakeServiceDataSource.films[0].description).assertIsDisplayed()
        }
    }

    @Test
    fun home_whenSearchItem_withQueryTotoro_shouldBeFound() {
        composeTestRule.apply {
            onNodeWithTextId(R.string.search)
                .assertIsDisplayed()
                .performTextInput("Totoro")
            onNodeWithText("My Neighbor Totoro").assertIsDisplayed()
        }
    }

    @Test
    fun home_whenSearchItem_withRandomInput_shouldNotFound() {
        composeTestRule.apply {
            onNodeWithTextId(R.string.search)
                .assertIsDisplayed()
                .performTextInput("asdasdasdasd")
            onNodeWithTextId(R.string.empty_search_result).assertIsDisplayed()
        }
    }

    @Test
    fun favorite_whenFirstOpen_shouldEmpty() {
        composeTestRule.apply {
            onNodeWithNavBarLabelId(R.string.favorite).performClick()
            onNodeWithTextId(R.string.empty_favorite).assertIsDisplayed()
        }
    }

    @Test
    fun favorite_afterAddFavorite_shouldNotEmpty() {
        composeTestRule.apply {
            onNodeWithText(FakeServiceDataSource.films[0].title).performClick()
            onNodeWithContentDescription("Favorite Button").performClick()
            onNodeWithContentDescriptionId(R.string.back).performClick()
            onNodeWithNavBarLabelId(R.string.favorite).performClick()
            onNodeWithTextId(R.string.empty_favorite).assertDoesNotExist()
            onNodeWithTag(FilmsListTestTag).assertIsDisplayed()
            onNodeWithText(FakeServiceDataSource.films[0].title).assertIsDisplayed()
        }
    }

    @Test
    fun about_whenOpened_shouldShowNameAndEmail() {
        composeTestRule.apply {
            onNodeWithNavBarLabelId(R.string.about_page).performClick()
            onNodeWithText("Abrar Wiryawan", ignoreCase = true).assertIsDisplayed()
            onNodeWithText("abrarwiryawan@gmail.com", ignoreCase = true).assertIsDisplayed()
        }
    }

}