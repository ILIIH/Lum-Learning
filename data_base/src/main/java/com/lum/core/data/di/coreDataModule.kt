package com.lum.core.data

import com.lum.core.SharedPrefManager.SharedPrefManager
import com.lum.core.SharedPrefManager.SharedPrefManagerIml
import com.lum.core.data.usecases.*
import com.lum.core.domain.repo.GameRepository
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
    single<GameRepository> {
        gameSettingsRepoImp(get())
    }
    single<SharedPrefManager> {
        SharedPrefManagerIml(get())
    }
    single {
        insertGameResult(get())
    }
}
