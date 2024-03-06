package com.example.about_theme_data

data class ThemeMetric(
    val AverageRA: Double,
    val AverageLastMonthRA: Double,
    val AverageThisMonthRA: Double,
    val title: String?,
    val imageUri: String?,
    val yearExperience: Int?,
    val themeImportance: String?,
    val themeType: Int,
)
