package com.example.add_theme_data

interface ThemeRepo {
    suspend fun saveThemes(theme: Theme)
}