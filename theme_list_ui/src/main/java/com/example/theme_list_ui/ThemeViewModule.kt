package com.example.theme_list_ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theme_list_data.GetTheme
import com.example.theme_list_data.Theme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ThemeViewModule(
    private val getTheme: GetTheme,
    private val navigator: themeListNavigation
) : ViewModel() {

    private val themes = MutableLiveData<List<Theme>>()
    val _themes: LiveData<List<Theme>>
        get() = themes

    fun LoadThemeList() {
        viewModelScope.launch {
            getTheme.execute().onEach {
                themes.postValue(it)
            }.launchIn(viewModelScope)
        }
    }

    fun toAddNewTheme() {
        navigator.toAddNewTheme()
    }
}
