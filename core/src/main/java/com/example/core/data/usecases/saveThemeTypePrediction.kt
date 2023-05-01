package com.example.core.data.usecases

import com.example.core.domain.models.learningMethod
import com.example.core.domain.repo.GameRepository
import kotlin.random.Random

class saveThemeTypePrediction (val repo: GameRepository) {
    suspend fun execute(memoTypeVariation: DoubleArray) {
        val maxProbability = memoTypeVariation.maxByOrNull { it }
        var themeType = 1
        if (memoTypeVariation[0] == maxProbability) themeType = 1
        if (memoTypeVariation[1] == maxProbability) themeType = 2
        if (memoTypeVariation[2] == maxProbability) themeType = 3


        val listOfLearningMethod = repo.getAllLearningMethod()

        if (listOfLearningMethod.size == 0) {
            repo.insertLearningMethod(
                learningMethod(
                    Random.nextInt(1, 6),
                    1.6,
                    Random.nextInt(1, 8),
                    Random.nextInt(1, 7),
                    themeType,
                )
            )
        } else {
            val lastIdItem = listOfLearningMethod.last()
            repo.insertLearningMethod(
                learningMethod(
                    lastIdItem.mnemoType,
                    lastIdItem.spacedRepetitionFactor,
                    lastIdItem.timeLearningInterval,
                    lastIdItem.learningDay,
                    themeType,
                )
            )
        }
    }
}
