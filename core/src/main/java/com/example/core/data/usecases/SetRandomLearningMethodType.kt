package com.example.core.data.usecases

import com.example.core.domain.models.learningMethod
import com.example.core.domain.repo.GameRepository
import kotlin.random.Random

class SetRandomLearningMethodType(val repo: GameRepository) {
    suspend fun execute() {
        repo.insertLearningMethod(
            learningMethod(
                Random.nextInt(1, 6),
                1.4, // TO_DO
                Random.nextInt(1, 8),
                Random.nextInt(1, 7),
                Random.nextInt(1, 3),
            ),
        )
    }
}
