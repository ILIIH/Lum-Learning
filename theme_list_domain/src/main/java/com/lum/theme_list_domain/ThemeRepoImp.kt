package com.lum.theme_list_domain

import com.lum.core.DB.ThemeDatabase
import com.lum.theme_list_data.Theme
import com.lum.theme_list_data.ThemeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ThemeRepoImp(val themeDB: ThemeDatabase) : ThemeRepo {
    override suspend fun getThemes(): Flow<List<Theme>> {
        return flow {
            val themeData = themeDB.themeDao().getAllTheme().map { it.toData() }
            emit( themeData)
        }
    }
}
