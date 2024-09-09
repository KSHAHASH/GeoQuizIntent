package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
private const val NOT_ANSWERED = 0
private const val CORRECT_ANSWER = 1
private const val INCORRECT_ANSWER = 2

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private val questionAnswers = mutableListOf(
        NOT_ANSWERED,
        NOT_ANSWERED,
        NOT_ANSWERED,
        NOT_ANSWERED,
        NOT_ANSWERED,
        NOT_ANSWERED)

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

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
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        binding.trueButton.setEnabled(questionAnswers[currentIndex] == NOT_ANSWERED)
        binding.falseButton.setEnabled(questionAnswers[currentIndex] == NOT_ANSWERED)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        //
        val messageResId = if (userAnswer == correctAnswer) {
            questionAnswers[currentIndex] = CORRECT_ANSWER
            R.string.correct_toast
        } else {
            questionAnswers[currentIndex] = INCORRECT_ANSWER
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