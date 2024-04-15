package com.lum.about_theme_data

interface GetThemeMetric {
    suspend fun execute(id: Int): ThemeMetric
}
