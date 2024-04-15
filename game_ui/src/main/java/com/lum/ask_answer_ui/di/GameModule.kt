package com.lum.ask_answer_ui.di

import com.lum.ask_answer_ui.viewModel.*
import com.lum.ask_answer_ui.viewModel.cardProvider
import com.lum.core.domain.Scopes
import org.koin.core.qualifier.named
import org.koin.dsl.module

val flashCardUiModule = module {
    scope(named(Scopes.GAME_SCOPE.scope)) {
        scoped { cardProvider(get(), get(), get()) }
    }
}
