package com.example.memorizable.di

import android.app.Application
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkerFactory
import com.example.about_theme_domain.di.aboutThemeModule
import com.example.about_theme_ui.di.aboutThemeUiModule
import com.example.add_new_card.di.addNewCardModule
import com.example.add_new_card_domain.di.cardModule
import com.example.add_theme_domain.di.addThemeDomainModule
import com.example.add_theme_ui.di.addThemeModule
import com.example.ask_answer_domain.di.askAnswerDomainModule
import com.example.ask_answer_ui.di.flashCardUiModule
import com.example.core.DB.di.RoomModule
import com.example.core.data.coreDataModule
import com.example.edit_ui.di.editCardUiModule
import com.example.navigation.di.navigationModule
import com.example.onboarding.di.onBoardingModule
import com.example.theme_list_domain.di.ThemeListDomainModule
import com.example.theme_list_ui.di.themeListModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

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
            )
        }
        setupWorkManagerFactory()

    }
}

fun setUpNNAPI(){

    val options = Interpreter.Options()
    var nnApiDelegate: NnApiDelegate? = null
    // Initialize interpreter with NNAPI delegate for Android Pie or above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        nnApiDelegate = NnApiDelegate()
        options.addDelegate(nnApiDelegate)
    }
    val assetManager = assets

    // Initialize TFLite interpreter
    val tfLite: Interpreter
    try {
        tfLite = Interpreter(loadModelFile(assetManager, "model.tflite"), options)
    } catch (e: Exception) {
        throw RuntimeException(e)
    }

}

fun Application.setupWorkManagerFactory() {
    getKoin().getAll<WorkerFactory>()
        .forEach {
            DelegatingWorkerFactory().addFactory(it)
        }
}
