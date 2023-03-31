package com.example.about_theme_ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.about_theme_data.GetThemeMetric
import com.example.about_theme_data.ThemeMetric
import com.example.core.data.usecases.getPredictedMnemoType
import com.example.core.domain.models.learningMethod
import kotlinx.coroutines.launch

class ThemeAboutViewModel(
    private val getTheme: GetThemeMetric,
    private val getPredictedMnemoType: getPredictedMnemoType,
) : ViewModel() {
    private val themeInfo = MutableLiveData<ThemeMetric>()
    val _themeInfo: LiveData<ThemeMetric>
        get() = themeInfo

    private val prefferedLearningMethod = MutableLiveData<learningMethod>()

    fun loadThemeInfo(id: Int) {
        viewModelScope.launch {
            themeInfo.postValue(getTheme.execute(id))
        }
    }

    fun getMnemoType() = prefferedLearningMethod.value!!.mnemoType
    fun getThemeType() = prefferedLearningMethod.value!!.themeType

    init {
        viewModelScope.launch {
            val result = getPredictedMnemoType.execute()
            prefferedLearningMethod.postValue(result!!)
        }
    }
}
