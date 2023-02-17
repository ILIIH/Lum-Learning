package com.example.theme_list_data

import kotlinx.coroutines.flow.Flow

interface GetTheme {
    suspend fun execute(): Flow<List<Theme>>
}