package com.lum.add_theme_data

data class Theme(
    val title: String,
    val imageUri: String,
    val yearExperience: Int,
    val themeImportance: String,
    val themeType: Int,
    val photo: ByteArray

)