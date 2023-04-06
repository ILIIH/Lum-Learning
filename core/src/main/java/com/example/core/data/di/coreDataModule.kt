package com.example.core.data

import com.example.core.data.usecases.*
import com.example.core.domain.repo.GameSettingsRepository
import org.koin.dsl.module

val coreDataModule = module {

    single {
        getBestPerfomanceMetric(get())
    }
    single {
        saveMnemoType(get())
    }
    single {
        SetRandomLearningMethodType(get())
    }
    single {
        getMnemoTypeDataset(get())
    }

    single {
        getSpacedFactorDataset(get())
    }
    single {
        saveSpacedFactor(get())
    }
    single {
        saveDayPrediction(get())
    }

    single {
        getDayPredictionDataset(get())
    }

    single {
        getThemeTypeDataset(get())
    }
    single {
        saveThemeTypePrediction(get())
    }

    single {
        saveTimeLearningPrediction(get())
    }

    single {
        getThemeLearningDataset(get())
    }

    single {
        getPredictedMnemoType(get())
    }
    single {
        getMnemoTypePrediction(get())
    }
    single<GameSettingsRepository> {
        gameSettingsRepoImp(get())
    }
}
