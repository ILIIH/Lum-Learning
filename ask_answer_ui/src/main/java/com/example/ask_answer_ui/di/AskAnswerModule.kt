package com.example.ask_answer_ui.di

import com.example.ask_answer_ui.viewModel.askAnswerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val askAnswerUiModule = module {
    viewModel {
        askAnswerViewModel(get())
    }
}