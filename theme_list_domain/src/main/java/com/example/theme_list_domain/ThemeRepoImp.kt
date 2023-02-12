package com.example.theme_list_domain

import android.content.Context
import com.example.theme_list_data.Theme
import com.example.theme_list_data.ThemeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ThemeRepoImp(val context: Context) : ThemeRepo {
    override fun getThemes(): Flow<List<Theme>> {
        return flow {
            emit(
                listOf(
                    ThemeDomain("Math", "").toData(),
                    ThemeDomain("Pharmasy", "").toData(),
                    ThemeDomain("Coocking", "").toData(),
                    ThemeDomain("Dates", "").toData(),
                    ThemeDomain("Programing", "").toData(),
                    ThemeDomain("Writings", "").toData(),

                )
            )
        }
    }
}
