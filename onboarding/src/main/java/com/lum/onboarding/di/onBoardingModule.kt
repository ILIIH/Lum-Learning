package com.lum.onboarding.di

import com.lum.add_theme_ui.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {
    viewModel {
        OnboardingViewModel(get())
    }
}