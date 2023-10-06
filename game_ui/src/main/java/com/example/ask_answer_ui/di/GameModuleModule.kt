package com.example.ask_answer_ui.di

import com.example.ask_answer_ui.viewModel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val flashCardUiModule = module {
    viewModel {
        VA_ViewModel(get())
    }
    viewModel {
        SA_ViewModel(get())
    }
    viewModel {
        DA_ViewModel(get())
    }
    viewModel {
        MC_ViewModel(get())
    }

    single {
        cardProvider(get(), get())
    }
}
