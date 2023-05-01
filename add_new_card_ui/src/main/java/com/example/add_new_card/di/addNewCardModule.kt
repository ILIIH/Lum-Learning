package com.example.add_new_card.di

import com.example.add_new_card.fragments.AddAudioCard.AddAudioCardViewmodel
import com.example.add_new_card.fragments.AddLearningCard.AddLearningCardViewmodel
import com.example.add_new_card.fragments.AddVisualCard.AddVisualCardViewmodel
import com.example.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addNewCardModule = module {
    viewModel {
        AddVisualCardViewmodel(get())
    }
    single {
        ThemeInfoProvider(get())
    }
    viewModel {
        AddAudioCardViewmodel(get())
    }
    viewModel {
        AddLearningCardViewmodel(get())
    }
}
