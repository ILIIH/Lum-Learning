package com.example.theme_list_ui.di
import com.example.theme_list_ui.ThemeViewModule
import com.example.theme_list_ui.adapter.ProfileAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val themeListModule = module {
    viewModel {
        ThemeViewModule(get(), get())
    }
    single {
        ProfileAdapter(get(), get())
    }
}
