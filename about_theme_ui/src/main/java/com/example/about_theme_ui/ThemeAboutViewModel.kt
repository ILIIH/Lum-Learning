package com.example.about_theme_ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.about_theme_data.GetThemeMetric
import com.example.about_theme_data.ThemeMetric
import com.example.core.data.usecases.getPredictedMnemoType
import com.example.core.domain.models.learningMethod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeAboutViewModel(
    private val getThemeMetric: GetThemeMetric,
    private val getPredictedMnemoType: getPredictedMnemoType,
) : ViewModel() {
    private val themeInfo = MutableStateFlow<ThemeMetric?>(null)
    val _themeInfo: StateFlow<ThemeMetric?>
        get() = themeInfo

    private val prefferedLearningMethod = MutableStateFlow<learningMethod?>(null)

    fun loadThemeInfo(id: Int) {
        viewModelScope.launch {
            val theme = getThemeMetric.execute(id)
            themeInfo.tryEmit(theme)
        }
    }

    init {
        viewModelScope.launch {
            val result = getPredictedMnemoType.execute()
            prefferedLearningMethod.tryEmit(result)
        }
    }
}
