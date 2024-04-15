package com.lum.add_theme_domain.mapper

import com.lum.add_theme_data.Theme
import com.lum.core.DB.Entities.ThemeEntity

fun Theme.toDatabase()  = ThemeEntity(
        themeName = this.title,
        photoThemeURI = this.imageUri,
        yearExperience = this.yearExperience,
        themeImportance = this.themeImportance,
        themeType = this.themeType,
        photo = photo
)
