package com.example.about_theme_data

interface GetThemeMetric {
    suspend fun execute(id: Int): ThemeMetric
}