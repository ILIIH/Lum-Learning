package com.example.about_theme_domain.di

import com.example.about_theme_data.GetThemeMetric
import com.example.about_theme_data.ThemeMetricRepo
import com.example.about_theme_domain.GetThemeMetricIml
import com.example.about_theme_domain.ThemeMetricRepoImp
import org.koin.dsl.module

val aboutThemeModule = module {
    single<ThemeMetricRepo> {
        ThemeMetricRepoImp(get(), get())
    }

    single<GetThemeMetric> {
        GetThemeMetricIml(get())
    }
}
