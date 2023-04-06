package com.example.add_new_card.di

import com.example.add_new_card.fragments.AddAudioCard.AddAudioCardViewmodel
import com.example.add_new_card.fragments.AddVisualCard.AddVisualCardViewmodel
import com.example.add_new_card.fragments.RuleFragment.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addNewCardModule = module {
    viewModel {
        AddVisualCardViewmodel(get())
    }
    viewModel {
        MainFragmentViewModel(get())
    }
    viewModel {
        AddAudioCardViewmodel(get())
    }
}
