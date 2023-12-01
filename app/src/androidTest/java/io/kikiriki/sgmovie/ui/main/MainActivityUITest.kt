package io.kikiriki.sgmovie.ui.main

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.ui.adapter.AdapterMovie
import io.kikiriki.sgmovie.utils.EspressoIdleResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityUITest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun onTestStart() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdleResource.get())
        activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @After
    fun onTestStop() {
        IdlingRegistry.getInstance().unregister(EspressoIdleResource.get())
    }

    @Test
    fun check_if_sort_dialog_is_displayed() {

        // perform click on dialog menu item
        onView(withId(R.id.action_sort)).perform(click())

        // check if dialog is displayed
        onView(withText(R.string.dialog_sort_by_lbl_title)).check(matches(isDisplayed()))
    }

    @Test
    fun check_if_item_click_show_detail_screen() {

        // perform click on the first item of item list
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition<AdapterMovie.ViewHolderNote>(0, click()))

        // check if detail view is displayed
        onView(withId(R.id.img_banner)).check(matches(isDisplayed()))

    }


}