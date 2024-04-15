package com.lum.theme_list_domain.di

import com.lum.theme_list_data.GetTheme
import com.lum.theme_list_data.ThemeRepo
import com.lum.theme_list_domain.GetThemeUsecase
import com.lum.theme_list_domain.ThemeRepoImp
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