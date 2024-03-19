package com.example.ask_answer_ui.di

import com.example.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.example.ask_answer_ui.viewModel.*
import com.example.core.domain.Scopes
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val flashCardUiModule = module {
    single {
        cardProvider(get(), get(), get())
    }
}
