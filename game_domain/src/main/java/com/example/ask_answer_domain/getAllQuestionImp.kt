package com.example.ask_answer_domain

import com.example.ask_answer_data.Question
import com.example.ask_answer_data.QuestionRepo
import com.example.ask_answer_data.getAllQuestion

class getAllQuestionImp(private val repo: QuestionRepo) : getAllQuestion {
    override fun execute(): List<Question> {
        return repo.getAllQuestion()
    }
}