package com.example.core.DB.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.core.DB.Entities.LearningMethod

@Dao
interface LearningMethodDAO {
    @Insert
    suspend fun insertLearningMethod(vararg method: LearningMethod)

    @Delete
    suspend fun deleteLearningMethod(vararg method: LearningMethod)

    @Query("SELECT * FROM learningMethod")
    suspend fun getAllLearningMethod(): List<LearningMethod>

    @Query("SELECT * FROM learningMethod WHERE id == :methodId")
    suspend fun getLearningMethodById(methodId: Int): LearningMethod
}