package io.kikiriki.sgmovie.ui.main

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kikiriki.sgmovie.R
import io.kikiriki.sgmovie.ui.adapter.AdapterMovie
import io.kikiriki.sgmovie.utils.EspressoIdleResource
import io.kikiriki.sgmovie.utils.RecyclerViewMatcher
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

    /**
     * 1. Click on item of the list
     * CHECK: if detail fragment is shown
     */
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

    /**
     * 1. Click on any item of the list.
     * 2. Detail View is shown.
     * 3. Click on favorite icon (to enable)
     * CHECK: if favorite icon is enabled in detail view
     *
     * 4. Click on favorite icon (to disable)
     * CHECK: if favorite icon is disabled in detail view
     */
    @Test
    fun check_if_favourite_flow_is_working_in_detail_view() {

        // in main activity: click on the first item position
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AdapterMovie.ViewHolderMovie>(
                0, click()
            ))

        // in detail view: mark movie as favourite
        onView(withId(R.id.lbl_favourite))
            .perform(click())

        // in detail view: check if favourite label is checked
        onView(withId(R.id.lbl_favourite))
            .check(matches(textViewContainsDrawable(R.drawable.ic_saved)))

        // mark as non favourite
        onView(withId(R.id.lbl_favourite))
            .perform(click())

        // check if favourite label is un_checked
        onView(withId(R.id.lbl_favourite))
            .check(matches(textViewContainsDrawable(R.drawable.ic_save)))
    }

    /**
     * 1. Click on favorite icon at the third position of the list  (to enable)
     * CHECK: if favorite icon at the third position of the list is enabled
     *
     * 2. Click on favorite icon at the third position of the list (to disable)
     * CHECK: if favorite icon at the third position of the list is disabled
     */
    @Test
    fun check_if_favourite_is_enabled_and_disabled_on_list_the_image_on_list_changes() {

        val itemPosition = 2

        // enable favorite the second item list
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AdapterMovie.ViewHolderMovie>(
                itemPosition, clickOnItemWithId(R.id.img_save)))

        // check if second list item is favorite
        onView(withRecyclerView(R.id.recycler_view).atPosition(itemPosition))
            .check(matches(imageViewContainsDrawable(R.id.img_save, R.drawable.ic_save)))

        // disable favorite the second item list
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AdapterMovie.ViewHolderMovie>(
                itemPosition, clickOnItemWithId(R.id.img_save)))

        // check if second list item is not favorite
        onView(withRecyclerView(R.id.recycler_view).atPosition(itemPosition))
            .check(matches(imageViewContainsDrawable(R.id.img_save, R.drawable.ic_saved)))

    }

    /**
     * 1. Click menu item sort
     * CHECK: if sort dialog is displayed
     */
    @Test
    fun check_if_sort_dialog_is_displayed() {

        // perform click on dialog menu item
        onView(withId(R.id.action_sort)).perform(click())

        // check if dialog is displayed
        onView(withText(R.string.dialog_sort_by_lbl_title)).check(matches(isDisplayed()))
    }

    /**
     * 1. Click on favorite icon at the first position of the list  (to enable)
     * 2. Click the first item position of the list
     * CHECK: if favorite icon is enabled in detail view
     */
    @Test
    fun check_if_favourite_is_click_on_list_then_open_detail_and_is_marked_as_favorite() {

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

    /**
     * 1. Click on favorite icon at the third position of the list (to enable)
     * 2. Open sort dialog
     * 3. In sort dialog, select the favorite option
     *
     * CHECK: if the first item on the list is favorite
     */
    @Test
    fun check_if_mark_second_item_as_favorite_then_reorder_by_favorite_is_successful() {

        // perform click on favourite button on third item
        val itemPosition = 2

        // perform click on favourite view in the first item view
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AdapterMovie.ViewHolderMovie>(
                itemPosition, clickOnItemWithId(R.id.img_save)
            ))

        // perform click on dialog menu item
        onView(withId(R.id.action_sort))
            .perform(click())

        // perform click on favorite sort method
        onView(withText(R.string.dialog_sort_by_lbl_favourites))
            .perform(click())

        // check if item at position 0 is favorite
        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
            .check(matches(hasDescendant(
                imageViewContainsDrawable(R.id.img_save, R.drawable.ic_saved)
            )))
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

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun withViewAtPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    private fun imageViewContainsDrawable(@IdRes imageViewId: Int, @DrawableRes drawableId: Int) = object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("ImageView with drawable same as drawable with id $drawableId")
        }

        override fun matchesSafely(view: View?): Boolean {
            val imageView = (view?.findViewById<View>(imageViewId)) as? ImageView
            if (imageView is ImageView) {
                val drawable = ContextCompat.getDrawable(view.context, drawableId)
                return areDrawablesIdentical(imageView.drawable, drawable)
            }
            return false
        }
    }

    private fun textViewContainsDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {

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

    private fun areDrawablesIdentical(drawableA: Drawable?, drawableB: Drawable?): Boolean {
        if (drawableA == null || drawableB == null) return false

        val stateA = drawableA.constantState
        val stateB = drawableB.constantState
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA == stateB
                || getBitmap(drawableA).sameAs(getBitmap(drawableB)))
    }

    private fun getBitmap(drawable: Drawable): Bitmap {
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