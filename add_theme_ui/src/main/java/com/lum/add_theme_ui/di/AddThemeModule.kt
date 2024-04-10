package com.lum.add_theme_ui.di

import com.lum.add_theme_ui.viewModels.ThemeAddViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addThemeModule = module {
    viewModel{
        ThemeAddViewModel()
    }
}