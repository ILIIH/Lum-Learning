package com.example.theme_list_ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ask_answer_data.ResultOf
import com.example.core.domain.ILError
import com.example.theme_list_data.GetTheme
import com.example.theme_list_data.Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ThemeViewModule(
    private val getTheme: GetTheme,
    private val navigator: ThemeListNavigation
) : ViewModel() {

    private val themes = MutableStateFlow<ResultOf<List<Theme>>>(ResultOf.Success(listOf()))
    val _themes: StateFlow<ResultOf<List<Theme>>>
        get() = themes

    fun loadThemeList() {
        val loadingTimerCoroutine = viewModelScope.launch {
            var mseconds = 0
            while (isActive) {
                if(mseconds == 2){
                    themes.tryEmit(ResultOf.Loading(listOf()))
                }
                mseconds++
                delay(100)
            }
        }

        viewModelScope.launch {
            try {
                getTheme.execute().onEach {
                    themes.tryEmit(ResultOf.Success(it))
                    loadingTimerCoroutine.cancel()
                }.launchIn(viewModelScope)

            } catch (e: Throwable) {
                loadingTimerCoroutine.cancel()
                themes.tryEmit(ResultOf.Failure(ILError.IO_ERROR))
            }
        }
    }

    fun toAddNewTheme() {
        navigator.toAddNewTheme()
    }
}
