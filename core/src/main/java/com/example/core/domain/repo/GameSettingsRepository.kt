package com.example.core.domain.repo


import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.core.DB.Entities.GameResults
import com.example.core.DB.Entities.LearningMethod
import com.example.core.domain.models.gameResult
import com.example.core.domain.models.learningMethod

interface GameSettingsRepository {

    suspend fun insertGameResult(result: gameResult)

    suspend fun deleteGameResult(result: gameResult)

    suspend fun getAllGameResult(): List<gameResult>

    suspend fun insertLearningMethod(method: learningMethod)

    suspend fun deleteLearningMethod(method: learningMethod)

    suspend fun getAllLearningMethod(): List<learningMethod>

    suspend fun getLearningMethodById(methodId: Int): learningMethod

}