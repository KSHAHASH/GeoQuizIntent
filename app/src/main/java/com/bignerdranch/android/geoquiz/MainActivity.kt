package com.bignerdranch.android.geoquiz
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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

    //from the CheatActivity we are requesting result code(Result_OK) and the intent(namely data)
    //to hear back from the child activity, we use Activity Results API-->registerForActivityResult
    //it takes two parameters
    //first--> contract that defines input(Intent) and output(ActivityResult) of the Activity you are trying to start
    //second--> lambda in which you parse the output that is returned, it is the output from first
    //ActivityResultContracts.StartActivityForResult() --> this contract specifies that the registered activity will
    //start another activity with an intent and expects a result back, i.e. RESULT_OK or RESULT_CANCELLED
    //{result ->  //handle result// } lambda function(with result parameter in it) that defines what to do when the result is received
    //result parameter contains resultCode-->integer(RESULT_OK or RESULT_CANCELLED), and data (an intent that may contain any extra data returned by the activity
    private val cheatLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result
        if (result.resultCode == Activity.RESULT_OK){
            //?. safely calls getBooleanExtra only if result.data is not null
            //you know getBooleanExtra should match the string and must provide the default value so there is false
            //in this case we have ?: elvis false to make sure in case result.data is null when no data was passed from activity
            quizViewModel.isCheater = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }


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

        binding.previousButton.setOnClickListener {
            if(quizViewModel.currentIndex >0){
                quizViewModel.moveToPrevious()
                val questionTextResId = quizViewModel.currentQuestionText
                binding.questionTextView.setText(questionTextResId)
            }
            else{
                quizViewModel.currentIndex = quizViewModel.questionBankSize -1
                val questionTextResId = quizViewModel.currentQuestionText
                binding.questionTextView.setText(questionTextResId)
            }
        }
        binding.cheatButton.setOnClickListener {
            //val intent = Intent(this, CheatActivity::class.java)
            // Start CheatActivity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
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
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
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