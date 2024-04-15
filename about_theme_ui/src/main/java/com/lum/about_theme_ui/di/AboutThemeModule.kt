package com.lum.about_theme_ui.di

import com.lum.about_theme_ui.ThemeAboutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val aboutThemeUiModule = module {
    viewModel {
        ThemeAboutViewModel(get(), get())
    }
}
