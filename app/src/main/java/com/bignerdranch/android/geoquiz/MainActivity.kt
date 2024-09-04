package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    //view binding generates classes based on layout file's name
    private lateinit var binding: ActivityMainBinding

    //to update the question and also for the textView binding as well
    //helps to provide the question in the next button too
    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textId
        binding.questionTextView.setText(questionTextResId)
    }

// after pressing true button we have to check whether the question bank's value and the buttons value matches or not
    //for that reason we have to create a function to check and call inside the listener
    private fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if(userAnswer == correctAnswer){
            R.string.correct_toast
        }
        else{
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),)

    private var currentIndex:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }


//        val questionTextResId = questionBank[currentIndex].textId
//        binding.questionTextView.setText(questionTextResId)
        //same thing, because we used the concept of encapsulation to not write same code again and again
        updateQuestion()


        //trueButton is the id of button in xml layout, it's same for all the views, naming convention with binding
        binding.trueButton.setOnClickListener {
            checkAnswer(true)

        }


        binding.falseButton.setOnClickListener{
            checkAnswer(false)
        }


        binding.nextButton.setOnClickListener {
            currentIndex =(currentIndex +1) % questionBank.size
            updateQuestion()
        }




    }
}