package com.example.core.data.usecases

import com.example.core.domain.models.learningMethod
import com.example.core.domain.repo.GameRepository
import kotlin.random.Random

class saveMnemoType(val repo: GameRepository) {
    suspend fun execute(memoTypeVariation: DoubleArray) {
        val maxProbability = memoTypeVariation.maxByOrNull { it }
        var mnemoTypeNumber = 0
        if (memoTypeVariation[0] == maxProbability) mnemoTypeNumber = 1
        if (memoTypeVariation[1] == maxProbability) mnemoTypeNumber = 2
        if (memoTypeVariation[2] == maxProbability) mnemoTypeNumber = 3
        if (memoTypeVariation[3] == maxProbability) mnemoTypeNumber = 4
        if (memoTypeVariation[4] == maxProbability) mnemoTypeNumber = 5
        if (memoTypeVariation[5] == maxProbability) mnemoTypeNumber = 6

        val listOfLearningMethod = repo.getAllLearningMethod()

        if (listOfLearningMethod.size == 0) {
            repo.insertLearningMethod(
                learningMethod(
                    mnemoTypeNumber,
                    Random.nextDouble(1.0, 2.0),
                    Random.nextInt(1, 8),
                    Random.nextInt(1, 7),
                    Random.nextInt(1, 3),
                ),
            )
        } else {
            val lastIdItem = listOfLearningMethod.last()
            repo.insertLearningMethod(
                learningMethod(
                    mnemoTypeNumber,
                    lastIdItem.spacedRepetitionFactor,
                    lastIdItem.timeLearningInterval,
                    lastIdItem.learningDay,
                    lastIdItem.themeType,
                ),
            )
        }
    }
}
