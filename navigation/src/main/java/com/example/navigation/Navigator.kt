package com.example.navigation

import androidx.navigation.NavController
import com.example.add_theme_ui.AddThemeNavigation

import com.example.onboarding.navigation.onboardingNavigation
import com.example.theme_list_ui.themeListNavigation

interface CoreNavigation {
    fun bind(navController: NavController)
    fun unbind()
}

class Navigator : CoreNavigation, onboardingNavigation, themeListNavigation, AddThemeNavigation {

    private var navController: NavController? = null

    override fun toAboutTheme() {
        navController?.navigate(R.id.to_about_theme)
    }

    override fun toAddNewTheme() {
        navController?.navigate(R.id.to_add_theme)
    }

    override fun toThemeList() {
        navController?.navigate(R.id.to_theme_list)
    }

    override fun toResetSettings() {
        TODO("Not yet implemented")
    }

    override fun bind(navController: NavController) {
        this.navController = navController
    }

    override fun unbind() {
        navController = null
    }

    override fun contin() {
        navController?.navigate(R.id.continue_to_theme_list)
    }
}
