package com.lum.theme_list_domain

import com.lum.core.DB.Entities.ThemeEntity
import com.lum.theme_list_data.Theme

fun ThemeEntity.toData() = Theme(this.themeName, this.photo, this.id)
