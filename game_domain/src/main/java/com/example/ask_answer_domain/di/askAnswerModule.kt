package com.example.ask_answer_domain.di

import com.example.ask_answer_data.QuestionRepo
import com.example.ask_answer_data.getAllQuestion
import com.example.ask_answer_domain.getAllQuestionImp
import org.koin.dsl.module

val askAnswerDomainModule = module {

    single<getAllQuestion> {
        getAllQuestionImp(get())
    }
}