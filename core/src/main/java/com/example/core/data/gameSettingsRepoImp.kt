package com.example.core.data

import com.example.core.DB.GamesDatabase
import com.example.core.domain.models.gameResult
import com.example.core.domain.models.learningMethod
import com.example.core.domain.models.toData
import com.example.core.domain.models.toDomain
import com.example.core.domain.repo.GameSettingsRepository

class gameSettingsRepoImp(val DB: GamesDatabase) : GameSettingsRepository {
    override suspend fun insertGameResult(result: gameResult) {
        DB.gamesDAO().insertGameResult(result.toData())
    }

    override suspend fun deleteGameResult(result: gameResult) {
        DB.gamesDAO().deleteGameResult(result.toData())
    }

    override suspend fun getAllGameResult(): List<gameResult> {
        return DB.gamesDAO().getAllGameResult().map { it.toDomain() }
    }

    override suspend fun insertLearningMethod(method: learningMethod) {
        DB.learningMethodDAO().insertLearningMethod(method.toData())
    }

    override suspend fun deleteLearningMethod(method: learningMethod) {
        DB.learningMethodDAO().deleteLearningMethod(method.toData())
    }

    override suspend fun getAllLearningMethod(): List<learningMethod> {
        return DB.learningMethodDAO().getAllLearningMethod()
            .sortedBy { it.id }
            .map { it.toDomain() }
    }

    override suspend fun getLearningMethodById(methodId: Int): learningMethod {
        TODO("Not yet implemented")
    }
}
