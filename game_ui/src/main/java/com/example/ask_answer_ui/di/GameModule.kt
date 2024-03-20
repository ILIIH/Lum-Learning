package com.example.ask_answer_ui.di

import com.example.ask_answer_ui.viewModel.*
import com.example.core.domain.Scopes
import org.koin.core.qualifier.named
import org.koin.dsl.module

val flashCardUiModule = module {
    scope(named(Scopes.GAME_SCOPE.scope)) {
        scoped { cardProvider(get(), get(), get()) }
    }
}
