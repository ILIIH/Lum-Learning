package com.example.ask_answer_ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ask_answer_data.Question
import com.example.ask_answer_data.getAllQuestion

class VA_ViewModel(private val getQuestions: getAllQuestion) : ViewModel() {
    private val question = MutableLiveData<Question>()
    val _question: LiveData<Question>
        get() = question
    init {
        question.postValue(getQuestions.execute().first())
    }
}
