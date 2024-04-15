package com.lum.about_theme_domain

import com.lum.about_theme_data.GetThemeMetric
import com.lum.about_theme_data.ThemeMetric
import com.lum.about_theme_data.ThemeMetricRepo

class GetThemeMetricIml(private val themeMetricRepo: ThemeMetricRepo) : GetThemeMetric {
    override suspend fun execute(id: Int): ThemeMetric {
        return themeMetricRepo.getThemeMetric(id)
    }
}
