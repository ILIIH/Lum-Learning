package com.example.core.data.usecases

import com.example.core.domain.models.learningMethod
import com.example.core.domain.repo.GameRepository
import kotlin.random.Random

class saveSpacedFactor(val repo: GameRepository) {
    suspend fun execute(memoTypeVariation: DoubleArray) {
        val maxProbability = memoTypeVariation.maxByOrNull { it }
        var spacedFactor = 1.0
        if (memoTypeVariation[0] == maxProbability) spacedFactor = 1.0
        if (memoTypeVariation[1] == maxProbability) spacedFactor = 1.2
        if (memoTypeVariation[2] == maxProbability) spacedFactor = 1.4
        if (memoTypeVariation[3] == maxProbability) spacedFactor = 1.8
        if (memoTypeVariation[4] == maxProbability) spacedFactor = 2.0

        val listOfLearningMethod = repo.getAllLearningMethod()

        if (listOfLearningMethod.size == 0) {
            repo.insertLearningMethod(
                learningMethod(
                    Random.nextInt(1, 6),
                    spacedFactor,
                    Random.nextInt(1, 8),
                    Random.nextInt(1, 7),
                    Random.nextInt(1, 3),
                ),
            )
        } else {
            val lastIdItem = listOfLearningMethod.last()
            repo.insertLearningMethod(
                learningMethod(
                    lastIdItem.mnemoType,
                    spacedFactor,
                    lastIdItem.timeLearningInterval,
                    lastIdItem.learningDay,
                    lastIdItem.themeType,
                ),
            )
        }
    }
}
