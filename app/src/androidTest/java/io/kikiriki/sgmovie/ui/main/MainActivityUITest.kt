package io.kikiriki.sgmovie.ui.main

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
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
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
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
        val itemPosition = 0
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AdapterMovie.ViewHolderMovie>(
                itemPosition, click()
            ))

        // check if detail view is displayed
        onView(withId(R.id.img_banner)).check(matches(isDisplayed()))

    }

    @Test
    fun check_if_favourite_is_working() {

        // perform click on favourite button on first item
        val itemPosition = 0

        // perform click on first item to open detail view
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AdapterMovie.ViewHolderMovie>(
                itemPosition, click()
            ))

        // mark as favourite
        onView(withId(R.id.lbl_favourite)).perform(click())

        // check if favourite label is checked
        onView(withId(R.id.lbl_favourite))
            .check(matches(textViewContainsDrawable(R.drawable.ic_saved)))

        // mark as non favourite
        onView(withId(R.id.lbl_favourite)).perform(click())

        // check if favourite label is un_checked
        onView(withId(R.id.lbl_favourite))
            .check(matches(textViewContainsDrawable(R.drawable.ic_save)))
    }

    @Test
    fun check_if_favourite_in_details() {

        // perform click on favourite button on first item
        val itemPosition = 0

        // perform click on favourite view in the first item view
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AdapterMovie.ViewHolderMovie>(
                itemPosition, clickOnItemWithId(R.id.img_save)
            ))

        // perform click on first item to open detail view
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AdapterMovie.ViewHolderMovie>(
                itemPosition, click()
            ))

        // check if favourite label is checked
        onView(withId(R.id.lbl_favourite))
            .check(matches(textViewContainsDrawable(R.drawable.ic_saved)))

    }

    private fun clickOnItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                (view.findViewById<View>(id))?.performClick()
            }
        }
    }

    fun textViewContainsDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("ImageView with drawable same as drawable with id $id")
        }

        override fun matchesSafely(view: View?): Boolean {
            if (view is TextView) {
                val drawable = ContextCompat.getDrawable(view.context, id)
                view.compoundDrawables.forEach { textViewDrawable ->
                    if(areDrawablesIdentical(textViewDrawable, drawable)){
                        return true
                    }
                }
                return false
            }
            return false
        }
    }

    fun areDrawablesIdentical(drawableA: Drawable?, drawableB: Drawable?): Boolean {
        if (drawableA == null || drawableB == null) return false

        val stateA = drawableA.constantState
        val stateB = drawableB.constantState
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA == stateB
                || getBitmap(drawableA).sameAs(getBitmap(drawableB)))
    }

    fun getBitmap(drawable: Drawable): Bitmap {
        val result: Bitmap
        if (drawable is BitmapDrawable) {
            result = drawable.bitmap
        } else {
            var width = drawable.intrinsicWidth
            var height = drawable.intrinsicHeight
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1
            }
            if (height <= 0) {
                height = 1
            }
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(result)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        }
        return result
    }


}