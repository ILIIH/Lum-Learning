package com.example.add_new_card.di

import com.example.add_new_card.fragments.AddAudioCard.AddAudioCardViewmodel
import com.example.add_new_card.fragments.AddLearningCard.AddLearningCardViewmodel
import com.example.add_new_card.fragments.AddVisualCard.AddVisualCardViewmodel
import com.example.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.example.core.domain.Scopes
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val addNewCardModule = module {
    viewModel {
        AddVisualCardViewmodel(get())
    }
    scope(named(Scopes.ADD_NEW_CARD_SCOPE.scope)) {
        scoped { ThemeInfoProvider(get()) }
    }
    viewModel {
        AddAudioCardViewmodel(get())
    }
    viewModel {
        AddLearningCardViewmodel(get())
    }
}
