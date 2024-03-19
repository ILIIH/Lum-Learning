package com.example.theme_list_ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theme_list_data.GetTheme
import com.example.theme_list_data.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ThemeViewModule(
    private val getTheme: GetTheme,
    private val navigator: ThemeListNavigation
) : ViewModel() {

    private val themes = MutableStateFlow<List<Theme>>(listOf())
    val _themes: StateFlow<List<Theme>>
        get() = themes

    fun loadThemeList() {
        viewModelScope.launch {
            getTheme.execute().onEach {
                themes.tryEmit(it)
            }.launchIn(viewModelScope)
        }
    }

    fun toAddNewTheme() {
        navigator.toAddNewTheme()
    }
}
