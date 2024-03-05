package com.example.plain_ui.di

import com.example.plain_ui.PlainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val planModule = module {
    viewModel {
        PlainViewModel(get())
    }
}