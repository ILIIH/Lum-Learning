package com.lum.theme_list_ui.di
import com.lum.theme_list_ui.ThemeViewModule
import com.lum.theme_list_ui.adapter.ThemeAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val themeListModule = module {
    viewModel {
        ThemeViewModule(get(), get())
    }
}
