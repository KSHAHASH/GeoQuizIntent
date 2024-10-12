package com.bignerdranch.android.geoquiz
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY)?:false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    //note currentIndex cannot be initialize above the questionBank because property
    //initialization happens in order, if you declare above then it will be initialized before QuestionBank
        var currentIndex:Int
        //get the Current_INDEX_KEY otherwise, if Current_index_key is null then, 0 will be the default value
        get()= savedStateHandle[CURRENT_INDEX_KEY] ?:0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
    //this setter function updates savedStateHandle when there is change in the currentIndex value in line 30
    //the currentIndex value will is the parameter for "value" and Current_Index_KEY will be updated with the "value"

    val questionBankSize: Int
        get() = questionBank.size

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious(){
       currentIndex = (currentIndex -1) % questionBank.size
    }



}