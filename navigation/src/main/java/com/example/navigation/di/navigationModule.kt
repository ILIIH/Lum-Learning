package com.example.navigation.di

import com.example.about_theme_ui.AboutThemeNavigation
import com.example.add_new_card.navigation.AddNewCardNavigation
import com.example.add_theme_ui.AddThemeNavigation
import com.example.ask_answer_ui.navigation.GameNavigation
import com.example.edit_ui.navigator.EditCardNavigation
import com.example.navigation.CoreNavigation
import com.example.navigation.Navigator
import com.example.onboarding.navigation.OnboardingNavigation
import com.example.plain_ui.navigation.PlainNavigation
import com.example.theme_list_ui.ThemeListNavigation
import org.koin.dsl.binds
import org.koin.dsl.module

val navigationModule = module {
    single { Navigator() } binds arrayOf(
        CoreNavigation::class,
        OnboardingNavigation::class,
        ThemeListNavigation::class,
        AddThemeNavigation::class,
        AboutThemeNavigation::class,
        PlainNavigation::class,
        AddNewCardNavigation::class,
        GameNavigation::class,
        EditCardNavigation::class
    )
}
