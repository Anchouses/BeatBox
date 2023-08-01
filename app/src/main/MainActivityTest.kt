package com.bignerdranch.beatbox

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.jetbrains.annotations.TestOnly
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.withSettings


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun showFirstFileName(){
        onView(withText("65_cjipie"))
            .check(matches(isDisplayed))
    }

}