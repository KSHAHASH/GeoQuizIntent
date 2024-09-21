package com.bignerdranch.android.geoquiz

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun showsFirstQuestionOnLaunch() {
        onView(withId(R.id.questionTextView))  //view matcher
            .check(matches(withText(R.string.question_australia))) //view assertion
    }


    //Instrumented test to verify when user clicks the NEXT button, they see second question in the quiz
    @Test
    fun showsSecondQuestionAfterNextPress() {
        onView(withId(R.id.nextButton)).perform(click())
        onView(withId(R.id.questionTextView))
            .check(matches(withText(R.string.question_oceans)))
    }


//test to verify that you fixed the UI state loss on rotation bug i.e., when you rotate screen
    @Test
    fun handlesActivityRecreation() {
        onView(withId(R.id.nextButton)).perform(click())
        scenario.recreate() //this function creates same situation as when you rotate device
        onView(withId(R.id.questionTextView))
            .check(matches(withText(R.string.question_oceans)))
    }
}