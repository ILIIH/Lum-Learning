package com.lum.about_theme_data

interface ThemeMetricRepo {
    suspend fun getThemeMetric(id: Int): ThemeMetric
}
