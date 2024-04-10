package com.lum.add_theme_data

interface SaveTheme {
    suspend fun execute(theme: Theme)
}