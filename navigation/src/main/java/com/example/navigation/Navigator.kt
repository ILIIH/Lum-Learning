package com.example.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.example.about_theme_ui.AboutThemeNavigation
import com.example.add_theme_ui.AddThemeNavigation
import com.example.ask_answer_ui.navigation.AskAnswerGameNav
import com.example.onboarding.navigation.onboardingNavigation
import com.example.theme_list_ui.themeListNavigation

interface CoreNavigation {
    fun bind(navController: NavController)
    fun unbind()
}

class Navigator :
    CoreNavigation,
    onboardingNavigation,
    themeListNavigation,
    AddThemeNavigation,
    AskAnswerGameNav,
    AboutThemeNavigation {

    private var navController: NavController? = null

    override fun toAboutTheme(id: Int) {
        val bundle = Bundle()
        bundle.putInt("id", id)
        navController?.navigate(R.id.to_about_theme, bundle)
    }

    override fun toAddNewTheme() {
        navController?.navigate(R.id.to_add_theme)
    }

    override fun toThemeList() {
        navController?.navigate(R.id.to_theme_list)
    }

    override fun toPlain() {
        navController?.navigate(R.id.to_plain_navigation_graph)
    }

    override fun bind(navController: NavController) {
        this.navController = navController
    }

    override fun unbind() {
        navController = null
    }

    override fun submit() {
        navController?.navigate(R.id.continue_to_theme_list)
    }

    override fun toTrain() {
        navController?.navigate(AboutThemeNavDirections.toAskAnswerGame())
    }

    override fun toEdit() {
        TODO("Not yet implemented")
    }

    override fun toCreate() {
        navController?.navigate(R.id.to_formula_builder)
    }

    override fun finshGame() {
        TODO("Not yet implemented")
    }
}

fun NavController.navigateSafe(directions: NavDirections, navOptions: NavOptions? = null) {
    currentDestination?.getAction(directions.actionId)?.let {
        navigate(directions, navOptions)
    }
}
