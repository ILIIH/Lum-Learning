package com.lum.navigation.di

import com.lum.about_theme_ui.AboutThemeNavigation
import com.lum.add_new_card.navigation.AddNewCardNavigation
import com.lum.add_theme_ui.AddThemeNavigation
import com.lum.ask_answer_ui.navigation.GameNavigation
import com.lum.edit_ui.navigator.EditCardNavigation
import com.lum.navigation.CoreNavigation
import com.lum.navigation.Navigator
import com.lum.onboarding.navigation.OnboardingNavigation
import com.lum.plain_ui.navigation.PlainNavigation
import com.lum.theme_list_ui.ThemeListNavigation
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
