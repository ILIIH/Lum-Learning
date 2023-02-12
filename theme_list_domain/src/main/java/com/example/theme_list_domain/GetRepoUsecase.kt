package com.example.theme_list_domain

import com.example.theme_list_data.GetTheme
import com.example.theme_list_data.ThemeRepo

class GetThemeUsecase(val themeRepo: ThemeRepo) : GetTheme {
    override fun execute() = themeRepo.getThemes()
}