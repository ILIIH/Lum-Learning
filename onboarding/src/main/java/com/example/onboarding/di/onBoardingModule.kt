package com.example.onboarding.di

import com.example.add_theme_ui.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {
    viewModel {
        OnboardingViewModel(get())
    }
}