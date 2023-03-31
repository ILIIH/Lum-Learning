package com.example.add_theme_domain.mapper

import com.example.add_theme_data.Theme
import com.example.core.DB.Entities.ThemeEntity

fun Theme.toDatabsce() = ThemeEntity(
    themeName = this.title,
    photoThemeURI = this.imageUri,
    yearExperience = this.yearExperience,
    themeImportance = this.themeImportance,
    themeType = this.themeType,
    photo = photo
)
