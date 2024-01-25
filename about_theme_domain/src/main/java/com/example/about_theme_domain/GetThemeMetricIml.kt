package com.example.about_theme_domain

import com.example.about_theme_data.GetThemeMetric
import com.example.about_theme_data.ThemeMetric
import com.example.about_theme_data.ThemeMetricRepo

class GetThemeMetricIml(private val themeMetricRepo: ThemeMetricRepo) : GetThemeMetric {
    override suspend fun execute(id: Int): ThemeMetric {
        return themeMetricRepo.getThemeMetric(id)
    }
}
