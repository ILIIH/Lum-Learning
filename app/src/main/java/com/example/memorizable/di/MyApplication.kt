package com.example.memorizable.di

import android.app.Application
import com.example.about_theme_domain.di.aboutThemeModule
import com.example.about_theme_ui.di.aboutThemeUiModule
import com.example.add_theme_domain.di.addThemeDomainModule
import com.example.add_theme_ui.di.addThemeModule
import com.example.core.DB.di.RoomModule
import com.example.navigation.di.navigationModule
import com.example.onboarding.di.onBoardingModule
import com.example.theme_list_domain.di.ThemeListDomainModule
import com.example.theme_list_ui.di.themeListModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                ThemeListDomainModule,
                themeListModule,
                RoomModule,
                addThemeModule,
                addThemeDomainModule,
                navigationModule,
                onBoardingModule,
                aboutThemeModule,
                aboutThemeUiModule,
                imageProviderModule
            )
        }
    }
}