package com.example.edit_ui.di

import com.example.edit_ui.card_list.CardListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editCardUiModule = module {
    viewModel<CardListViewModel> {
        CardListViewModel(get())
    }
}
