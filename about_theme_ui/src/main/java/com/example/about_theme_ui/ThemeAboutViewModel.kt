package com.example.about_theme_ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.about_theme_data.GetThemeMetric
import com.example.about_theme_data.ThemeMetric
import kotlinx.coroutines.launch

class ThemeAboutViewModel(
    private val getTheme: GetThemeMetric,
    private val navigator: AddThemeNavigation
) : ViewModel() {
    private val themeInfo = MutableLiveData<ThemeMetric>()
    val _themeInfo: LiveData<ThemeMetric>
        get() = themeInfo

    fun loadThemeInfo(id: Int) {
        viewModelScope.launch {
            themeInfo.postValue(getTheme.execute(id))
        }
    }

    fun toTrainingScreen() {
        navigator.toTrain()
    }

    fun toEditScreen() {
        navigator.toEdit()
    }

    fun toCreateScreen() {
        navigator.toCreate()
    }
}
