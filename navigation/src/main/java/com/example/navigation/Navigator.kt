package com.example.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.example.about_theme_ui.AboutThemeNavigation
import com.example.add_new_card.navigation.AddNewCardNavigation
import com.example.add_theme_ui.AddThemeNavigation
import com.example.ask_answer_ui.navigation.GameNavigation
import com.example.edit_ui.navigator.EditCardNavigation
import com.example.onboarding.navigation.OnboardingNavigation
import com.example.plain_ui.navigation.PlainNavigation
import com.example.theme_list_ui.ThemeListNavigation

interface CoreNavigation {
    fun bind(navController: NavController)
    fun unbind()
}

class Navigator :
    CoreNavigation,
    OnboardingNavigation,
    ThemeListNavigation,
    AddThemeNavigation,
    GameNavigation,
    AboutThemeNavigation,
    PlainNavigation,
    AddNewCardNavigation,
    EditCardNavigation
{

    override fun bind(navController: NavController) {
        this.navController = navController
    }

    override fun unbind() {
        navController = null
    }

    private var navController: NavController? = null

    override fun toAboutTheme(id: Int) {
        navController?.navigateSafe(ThemeListNavDirections.toAboutTheme(id))
    }

    override fun toAddNewTheme() {
        navController?.navigateSafe(ThemeListNavDirections.toAddTheme())
    }

    override fun toThemeList() {
        navController?.navigateSafe(OnboardingNavDirections.toThemeList())
    }

    override fun toPlain() {
        navController?.navigateSafe(OnboardingNavDirections.toPlainNavigationGraph())
    }

    override fun submitNewTheme() {
        navController?.navigateSafe(AddNewThemeNavDirections.continueToThemeList())
    }

    override fun toTrain(themeId: Int ){
        navController?.navigateSafe(AboutThemeNavDirections.toAskAnswerGame(themeId))
    }

    override fun toEdit(themeId: Int) {
        navController?.navigateSafe(AboutThemeNavDirections.toEditCard(themeId))
    }

    override fun toCreateNewCard(themeId: Int) {
        navController?.navigateSafe(AboutThemeNavDirections.fromAboutThemeToAddNewCard(themeId))
    }


    override fun fromGameToAddNewCard(themeId: Int) {
        navController?.navigateSafe(AskAnswerUiNavigationDirections.fromGameToAddNewCard(themeId))

    }

    override fun toCreateNewTheme() {
        navController?.navigate(R.id.from_plain_to_create_theme)
    }

    override fun saveNewCardAndExit(themeId: Int) {

        navController?.navigateSafe(AddNewCardGraphDirections.saveNewCardAndExit(themeId))
    }

    override fun fromEditCardToAddNewCard(themeId: Int) {
        navController?.navigateSafe(EditCardsNavigationDirections.actionGlobalAddNewCardGraph(themeId))
    }

}

fun NavController.navigateSafe(directions: NavDirections, navOptions: NavOptions? = null) {
    currentDestination?.getAction(directions.actionId)?.let {
        navigate(directions, navOptions)
    }
}
