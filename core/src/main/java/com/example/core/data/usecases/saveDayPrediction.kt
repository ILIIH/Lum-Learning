package com.example.core.data.usecases

import com.example.core.domain.models.learningMethod
import com.example.core.domain.repo.GameSettingsRepository
import kotlin.random.Random

class saveDayPrediction(val repo: GameSettingsRepository) {
    suspend fun execute(memoTypeVariation: DoubleArray) {
        val maxProbability = memoTypeVariation.maxByOrNull { it }
        var dayPrediction = 1
        if (memoTypeVariation[0] == maxProbability) dayPrediction = 1
        if (memoTypeVariation[1] == maxProbability) dayPrediction = 2
        if (memoTypeVariation[2] == maxProbability) dayPrediction = 3
        if (memoTypeVariation[3] == maxProbability) dayPrediction = 4
        if (memoTypeVariation[4] == maxProbability) dayPrediction = 5
        if (memoTypeVariation[5] == maxProbability) dayPrediction = 6
        if (memoTypeVariation[6] == maxProbability) dayPrediction = 7

        val listOfLearningMethod = repo.getAllLearningMethod()

        if (listOfLearningMethod.isEmpty()) {
            repo.insertLearningMethod(
                learningMethod(
                    Random.nextInt(1, 6),
                    1.8,
                    Random.nextInt(1, 8),
                    dayPrediction,
                    Random.nextInt(1, 3),
                ),
            )
        } else {
            val lastIdItem = listOfLearningMethod.last()
            repo.insertLearningMethod(
                learningMethod(
                    lastIdItem.mnemoType,
                    lastIdItem.spacedRepetitionFactor,
                    lastIdItem.timeLearningInterval,
                    dayPrediction,
                    lastIdItem.themeType,
                ),
            )
        }
    }
}
