package com.example.theme_list_domain.di

import com.example.theme_list_data.GetTheme
import com.example.theme_list_data.ThemeRepo
import com.example.theme_list_domain.GetThemeUsecase
import com.example.theme_list_domain.ThemeRepoImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val ThemeListDomainModule = module {

    single<GetTheme> {
        GetThemeUsecase(get())
    }

    single<ThemeRepo> {
        ThemeRepoImp(get())
    }
}