package io.kikiriki.sgmovie.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdleResource {

    private const val debugMode = true
    private val name = CountingIdlingResource::class.java.simpleName
    private val countingIdlingResource = CountingIdlingResource(name, debugMode)

    fun increment() = countingIdlingResource.increment()

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

    fun get() = countingIdlingResource

}