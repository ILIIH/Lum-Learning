package com.lum.theme_list_domain

import com.lum.theme_list_data.GetTheme
import com.lum.theme_list_data.ThemeRepo

class GetThemeUsecase(private val themeRepo: ThemeRepo) : GetTheme {
    override suspend fun execute() = themeRepo.getThemes()
}