package com.example.memorizable.di

import android.app.Application
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkerFactory
import com.example.about_theme_domain.di.aboutThemeModule
import com.example.about_theme_ui.di.aboutThemeUiModule
import com.example.add_new_card_domain.di.cardModule
import com.example.add_theme_domain.di.addThemeDomainModule
import com.example.add_theme_ui.di.addThemeModule
import com.example.ask_answer_domain.di.askAnswerDomainModule
import com.example.ask_answer_ui.di.askAnswerUiModule
import com.example.core.DB.di.RoomModule
import com.example.core.data.coreDataModule
import com.example.navigation.di.navigationModule
import com.example.onboarding.di.onBoardingModule
import com.example.theme_list_domain.di.ThemeListDomainModule
import com.example.theme_list_ui.di.themeListModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class MyApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            workManagerFactory()
            modules(
                cardModule,
                workerModule,
                ThemeListDomainModule,
                themeListModule,
                RoomModule,
                addThemeModule,
                addThemeDomainModule,
                navigationModule,
                onBoardingModule,
                aboutThemeModule,
                aboutThemeUiModule,
                imageProviderModule,
                askAnswerUiModule,
                askAnswerDomainModule,
                coreDataModule
            )
        }
        setupWorkManagerFactory()
    }
}

fun Application.setupWorkManagerFactory() {
    getKoin().getAll<WorkerFactory>()
        .forEach {
            DelegatingWorkerFactory().addFactory(it)
        }
}
