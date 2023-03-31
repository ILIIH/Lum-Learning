package com.example.theme_list_domain

import com.example.core.DB.Entities.ThemeEntity
import com.example.theme_list_data.Theme

fun ThemeEntity.toData() = Theme(this.themeName, this.photo, this.id)
