package com.example.add_theme_domain

import com.example.add_theme_data.Theme
import com.example.add_theme_data.ThemeRepo
import com.example.add_theme_domain.mapper.toDatabase
import com.example.core.DB.ThemeDatabase

class ThemeRepository(private val themeDB: ThemeDatabase) : ThemeRepo {
    override suspend fun saveThemes(theme: Theme) {
        themeDB.themeDao().insertTheme(theme.toDatabase())
    }
}


