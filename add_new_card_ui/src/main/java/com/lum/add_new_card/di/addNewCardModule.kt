package com.lum.add_new_card.di

import com.lum.add_new_card.adapters.AddCardAnimations
import com.lum.add_new_card.fragments.AddAudioCard.AddAudioCardViewmodel
import com.lum.add_new_card.fragments.AddLearningCard.AddLearningCardViewmodel
import com.lum.add_new_card.fragments.AddVisualCard.AddVisualCardViewmodel
import com.lum.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.lum.core.domain.Scopes
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
    scope(named(Scopes.ADD_NEW_CARD_SCOPE.scope)){
        scoped { AddCardAnimations() }
    }
    viewModel {
        AddAudioCardViewmodel(get())
    }
    viewModel {
        AddLearningCardViewmodel(get())
    }
}
