package com.example.theme_list_domain

import android.util.Log
import com.example.core.DB.ThemeDatabace
import com.example.theme_list_data.Theme
import com.example.theme_list_data.ThemeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ThemeRepoImp(val themeDB: ThemeDatabace) : ThemeRepo {
    override suspend fun getThemes(): Flow<List<Theme>> {
        return flow {
            val themeData = themeDB.themeDao().getAllTheme().map { it.toData() }
            Log.d("theme_test", themeData.toString())
            emit(
                themeData
            )
        }
    }
}
