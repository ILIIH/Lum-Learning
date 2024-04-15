package com.lum.theme_list_data

import kotlinx.coroutines.flow.Flow

interface ThemeRepo {
    suspend fun getThemes(): Flow<List<Theme>>
}