package com.lum.core.data

import com.lum.core.DB.GamesDatabase
import com.lum.core.domain.models.gameResult
import com.lum.core.domain.models.learningMethod
import com.lum.core.domain.models.toData
import com.lum.core.domain.models.toDomain
import com.lum.core.domain.repo.GameRepository

class gameSettingsRepoImp(val DB: GamesDatabase) : GameRepository {
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
        return DB.learningMethodDAO().getAllLearningMethod().sortedBy { it.id == methodId  }.first().toDomain()
    }

    override suspend fun getCurrentLearningMethodId(): Int {
        return DB.learningMethodDAO().getAllLearningMethod().sortedBy { it.id }.last().id
    }
}
