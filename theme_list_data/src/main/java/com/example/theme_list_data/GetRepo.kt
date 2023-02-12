package com.example.theme_list_data

import kotlinx.coroutines.flow.Flow

interface GetTheme {
    fun execute(): Flow<List<Theme>>
}