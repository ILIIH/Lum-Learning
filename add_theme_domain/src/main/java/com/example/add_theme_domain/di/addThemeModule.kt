package com.example.add_theme_domain.di

import com.example.add_theme_data.SaveTheme
import com.example.add_theme_data.ThemeRepo
import com.example.add_theme_domain.SaveThemeUsecase
import com.example.add_theme_domain.ThemeRepository
import org.koin.dsl.module

val addThemeDomainModule = module {

    single<SaveTheme> {
        SaveThemeUsecase(get())
    }

    single<ThemeRepo> {
        ThemeRepository(get())
    }
}