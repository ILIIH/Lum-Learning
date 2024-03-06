package com.example.plain_ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theme_list_data.GetTheme
import com.example.theme_list_data.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlainViewModel(private val getThemeUseCase: GetTheme) : ViewModel() {

    private val _themes =  MutableStateFlow<List<Theme>>(listOf())
    val themes: StateFlow<List<Theme>>
        get() = _themes

    init {
        viewModelScope.launch {
            getThemeUseCase.execute().onEach { _themes.tryEmit(it) }.launchIn(viewModelScope)
        }

    }
}