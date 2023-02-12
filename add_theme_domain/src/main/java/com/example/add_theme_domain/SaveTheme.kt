package com.example.add_theme_domain

import com.example.add_theme_data.SaveTheme
import com.example.add_theme_data.Theme
import com.example.add_theme_data.ThemeRepo

class SaveThemeUsecase(private val repository: ThemeRepo) : SaveTheme {
    override suspend fun execute(theme: Theme) {
        repository.saveThemes(theme)
    }
}