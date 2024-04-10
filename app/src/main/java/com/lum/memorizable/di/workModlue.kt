package com.lum.memorizable.di

import com.lum.ai.workers.*
import com.lum.ai.workers.DayPredictionWorker
import com.lum.ai.workers.MnemoTypeWorker
import com.lum.ai.workers.SpacedFactorWorker
import com.lum.ai.workers.ThemeTypeWorker
import com.lum.ai.workers.TimeLearningWorker
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    worker<MnemoTypeWorker> { MnemoTypeWorker(get(), get(), get(), get(),get(),get()) }

    worker<SpacedFactorWorker> { SpacedFactorWorker(get(), get(), get(), get(),get(),get()) }

    worker<DayPredictionWorker> { DayPredictionWorker(get(), get(), get(), get(),get(),get()) }

    worker<ThemeTypeWorker> { ThemeTypeWorker(get(), get(), get(), get(),get(),get()) }

    worker<TimeLearningWorker> { TimeLearningWorker(get(), get(), get(), get(),get(),get()) }

}
