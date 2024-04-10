package com.lum.core.domain.repo


import com.lum.core.domain.models.gameResult
import com.lum.core.domain.models.learningMethod

interface GameRepository {

    suspend fun insertGameResult(result: gameResult)

    suspend fun deleteGameResult(result: gameResult)

    suspend fun getAllGameResult(): List<gameResult>

    suspend fun insertLearningMethod(method: learningMethod)

    suspend fun deleteLearningMethod(method: learningMethod)

    suspend fun getAllLearningMethod(): List<learningMethod>

    suspend fun getLearningMethodById(methodId: Int): learningMethod

    suspend fun getCurrentLearningMethodId(): Int

}