package com.example.core.data.usecases

import com.example.core.domain.models.gameResult
import com.example.core.domain.repo.GameSettingsRepository

class getBestPerfomanceMetric(val repo: GameSettingsRepository) {
    suspend fun execute(): DoubleArray {
        val bestPerfomanceMetric = repo.getAllGameResult()
            .minByOrNull {
                it.K * 0.1 + it.D * 0.3 + it.Ch * 0.6
            }

        //                          K     D    Ch   T     Tw1   Tw2
        return doubleArrayOf(
            bestPerfomanceMetric!!.K,
            bestPerfomanceMetric.D,
            bestPerfomanceMetric.Ch,
            bestPerfomanceMetric.T,
            bestPerfomanceMetric.Tw1,
            bestPerfomanceMetric.Tw2)
    }
}
