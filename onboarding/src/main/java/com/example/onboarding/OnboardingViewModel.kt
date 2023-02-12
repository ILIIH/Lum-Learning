package com.example.add_theme_ui

import androidx.lifecycle.ViewModel
import com.example.onboarding.navigation.onboardingNavigation

class OnboardingViewModel(
    private val navigator: onboardingNavigation
) : ViewModel() {

    fun navigateToThemeList() {
        navigator.toThemeList()
    }
}
