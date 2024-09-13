package com.bignerdranch.android.geoquiz
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    //note currentIndex cannot be initialize above the questionBank because property
    //initialization happens in order, if you declare above then it will be initialized before QuestionBank
        var currentIndex:Int
        //get the Current_INDEX_KEY otherwise, if Current_index_key is null then, 0 will be the default value
        get()= savedStateHandle[CURRENT_INDEX_KEY] ?:0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
    //

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }



}