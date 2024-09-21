package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

class QuizViewModelTest{
    @Test
    //test to verify that the QuizViewModel provides correct question
    // text for the first question just after it it initialized
    fun providesExpectedQuestionText(){
        //constructor parameter for QuizViewModel is only savedStateHandle
        //so initialize a saved instance state with just an empty constructor
        //so that you can initialize a QuizViewModel
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    //second test is to verify the behavior when you are at the end of question
    //and move to the next question, it should wrap around to the first question

    @Test
    fun wrapsAroundQuestionBank() {
        //last question in the question bank
        //holds the key and value, where key is CURRENT_INDEX_KEY and its value is 5

        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    //Challenge Question from the Chapter 6 Testing, implementing assertTrue
    //assertTrue() to verify currentQuestionAnswer(which has boolean value) checking that with the string
    @Test
    fun testingCurrentQuestionAnswer(){
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)

        // Ensure the currentIndex is set to the first question (index 0)
        assertEquals(0, quizViewModel.currentIndex)

        //checks if the currentQuestionAnswer booleans value is True, if True then executes
        assertTrue(quizViewModel.currentQuestionAnswer) // assuming firstQuestion is True
    }


}