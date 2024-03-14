package com.example.memorizable.di

import com.example.ai.workers.*
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    worker<MnemoTypeWorker> { MnemoTypeWorker(get(), get(), get(), get(),get(),get()) }

    worker<SpacedFactorWorker> { SpacedFactorWorker(get(), get(), get(), get(),get(),get()) }

    worker<DayPredictionWorker> { DayPredictionWorker(get(), get(), get(), get(),get(),get()) }

    worker<ThemeTypeWorker> { ThemeTypeWorker(get(), get(), get(), get(),get(),get()) }

    worker<TimeLearningWorker> { TimeLearningWorker(get(), get(), get(), get(),get(),get()) }

}
