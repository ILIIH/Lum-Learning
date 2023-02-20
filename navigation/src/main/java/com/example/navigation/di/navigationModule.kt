package com.example.navigation.di

import com.example.about_theme_ui.AboutThemeNavigation
import com.example.add_theme_ui.AddThemeNavigation
import com.example.navigation.CoreNavigation
import com.example.navigation.Navigator
import com.example.onboarding.navigation.onboardingNavigation
import com.example.theme_list_ui.themeListNavigation
import org.koin.dsl.binds
import org.koin.dsl.module

val navigationModule = module {
    single { Navigator() } binds arrayOf(
        CoreNavigation::class,
        onboardingNavigation::class,
        themeListNavigation::class,
        AddThemeNavigation::class,
        AboutThemeNavigation::class
    )
}
