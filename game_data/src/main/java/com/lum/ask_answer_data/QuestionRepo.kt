package com.lum.ask_answer_data

interface QuestionRepo {
    fun getAllQuestion(): List<Question>
}