package com.lum.memorizable.di

import android.app.Application
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkerFactory
import com.lum.about_theme_domain.di.aboutThemeModule
import com.lum.about_theme_ui.di.aboutThemeUiModule
import com.lum.add_new_card.di.addNewCardModule
import com.lum.add_new_card_domain.di.cardModule
import com.lum.add_theme_domain.di.addThemeDomainModule
import com.lum.add_theme_ui.di.addThemeModule
import com.lum.ask_answer_domain.di.askAnswerDomainModule
import com.lum.ask_answer_ui.di.flashCardUiModule
import com.lum.core.DB.di.RoomModule
import com.lum.core.data.coreDataModule
import com.lum.edit_ui.di.editCardUiModule
import com.lum.navigation.di.navigationModule
import com.lum.onboarding.di.onBoardingModule
import com.lum.plain_domain.di.plainDomainModule
import com.lum.plain_ui.di.planModule
import com.lum.theme_list_domain.di.ThemeListDomainModule
import com.lum.theme_list_ui.di.themeListModule
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
                addNewCardModule,
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
                flashCardUiModule,
                askAnswerDomainModule,
                coreDataModule,
                editCardUiModule,
                planModule,
                plainDomainModule
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
