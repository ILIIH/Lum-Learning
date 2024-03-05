package com.example.add_theme_ui

import androidx.lifecycle.ViewModel
import com.example.onboarding.navigation.OnboardingNavigation

class OnboardingViewModel(
    private val navigator: OnboardingNavigation
) : ViewModel() {

    fun navigateToThemeList() {
        navigator.toThemeList()
    }

    fun navigateToPlain() {
        navigator.toPlain()
    }
}
