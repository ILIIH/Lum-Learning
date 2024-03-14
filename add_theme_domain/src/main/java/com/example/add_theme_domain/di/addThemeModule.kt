package com.example.add_theme_domain.di

import com.example.add_theme_data.SaveTheme
import com.example.add_theme_data.ThemeRepo
import com.example.add_theme_domain.SaveThemeUsecase
import com.example.add_theme_domain.ThemeRepository
import com.example.core.domain.Scopes
import org.koin.core.qualifier.named
import org.koin.dsl.module

val addThemeDomainModule = module {
    scope(named(Scopes.ADD_NEW_THEME_SCOPE)) {
        scoped<SaveTheme> { SaveThemeUsecase(get()) }
        scoped<ThemeRepo> { ThemeRepository(get()) }
    }
}