package com.lum.core.data.usecases

import com.lum.core.domain.models.learningMethod
import com.lum.core.domain.repo.GameRepository
import kotlin.random.Random

class SetRandomLearningMethodType(val repo: GameRepository) {
    suspend fun execute() {
        repo.insertLearningMethod(
            learningMethod(
                Random.nextInt(1, 6),
                1.4, // TODO: Change approach
                Random.nextInt(1, 8),
                Random.nextInt(1, 7),
                Random.nextInt(1, 3),
            ),
        )
    }
}
