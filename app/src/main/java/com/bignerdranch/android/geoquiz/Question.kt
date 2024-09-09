package com.bignerdranch.android.geoquiz
import androidx.annotation.StringRes


//string annotation is not required but it prevents runtime crashes
//where constructor is used with invalid resource ID (such as an ID that points to some resource other than a string)
data class Question (@StringRes val textResId: Int,
                     val answer:Boolean,
                   ){

}