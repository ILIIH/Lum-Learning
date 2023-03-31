package com.example.core.data.usecases

import com.example.core.domain.models.learningMethod
import com.example.core.domain.repo.GameSettingsRepository
import kotlin.random.Random

class saveTimeLearningPrediction (val repo: GameSettingsRepository) {
    suspend fun execute(memoTypeVariation: DoubleArray) {
        val maxProbability = memoTypeVariation.maxByOrNull { it }
        var themeLearningInterval = 1
        if (memoTypeVariation[0] == maxProbability) themeLearningInterval = 1
        if (memoTypeVariation[1] == maxProbability) themeLearningInterval = 2
        if (memoTypeVariation[2] == maxProbability) themeLearningInterval = 3
        if (memoTypeVariation[3] == maxProbability) themeLearningInterval = 4
        if (memoTypeVariation[4] == maxProbability) themeLearningInterval = 5
        if (memoTypeVariation[5] == maxProbability) themeLearningInterval = 6
        if (memoTypeVariation[6] == maxProbability) themeLearningInterval = 7
        if (memoTypeVariation[7] == maxProbability) themeLearningInterval = 8

        val listOfLearningMethod = repo.getAllLearningMethod()

        if (listOfLearningMethod.size == 0) {
            repo.insertLearningMethod(
                learningMethod(
                    Random.nextInt(1, 6),
                    2.0,
                    themeLearningInterval,
                    Random.nextInt(1, 7),
                    Random.nextInt(1, 3),
                )
            )
        } else {
            val lastIdItem = listOfLearningMethod.last()
            repo.insertLearningMethod(
                learningMethod(
                    lastIdItem.mnemoType,
                    lastIdItem.spacedRepetitionFactor,
                    themeLearningInterval,
                    lastIdItem.learningDay,
                    lastIdItem.themeType,
                )
            )
        }
    }
}
