package com.lum.ask_answer_domain

import com.lum.ask_answer_data.Question
import com.lum.ask_answer_data.QuestionRepo
import com.lum.ask_answer_data.getAllQuestion

class getAllQuestionImp(private val repo: QuestionRepo) : getAllQuestion {
    override fun execute(): List<Question> {
        return repo.getAllQuestion()
    }
}