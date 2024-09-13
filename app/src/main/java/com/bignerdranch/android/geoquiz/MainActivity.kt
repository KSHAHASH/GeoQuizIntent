package com.bignerdranch.android.geoquiz
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
private const val NOT_ANSWERED = 0
private const val CORRECT_ANSWER = 1
private const val INCORRECT_ANSWER = 2

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //instance of the QuizViewModel
    private val quizViewModel : QuizViewModel by viewModels()


    private val questionAnswers = mutableListOf(
        NOT_ANSWERED,
        NOT_ANSWERED,
        NOT_ANSWERED,
        NOT_ANSWERED,
        NOT_ANSWERED,
        NOT_ANSWERED)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()
    }

    //We define and override functions below and invoke them above

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)

        binding.trueButton.setEnabled(questionAnswers[quizViewModel.currentIndex] == NOT_ANSWERED)
        binding.falseButton.setEnabled(questionAnswers[quizViewModel.currentIndex] == NOT_ANSWERED)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        //
        val messageResId = if (userAnswer == correctAnswer) {
            questionAnswers[quizViewModel.currentIndex] = CORRECT_ANSWER
            R.string.correct_toast
        } else {
            questionAnswers[quizViewModel.currentIndex] = INCORRECT_ANSWER
            R.string.incorrect_toast
        }

        binding.trueButton.setEnabled(false)
        binding.falseButton.setEnabled(false)

        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()

        if (!questionAnswers.contains(NOT_ANSWERED)) {
            val correctAnswers = questionAnswers.filter { n:Int -> n == CORRECT_ANSWER }
            val correctPercent = 100.0 * correctAnswers.size / questionAnswers.size
            Toast.makeText(this,"You got $correctPercent% correct!",Toast.LENGTH_LONG).show()
        }
    }
}