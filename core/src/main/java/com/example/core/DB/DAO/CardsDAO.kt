package com.example.core.DB.DAO

import androidx.room.*
import com.example.core.DB.Entities.AudioLearningCard
import com.example.core.DB.Entities.LearningCrad
import com.example.core.DB.Entities.VisualLearningCard

@Dao
interface CardsDAO {
    @Insert
    suspend fun insertLearningCrad(vararg card: LearningCrad)

    @Delete
    suspend fun deleteLearningCrad(vararg card: LearningCrad)

    @Update
    suspend fun changeLearningCrad(vararg card: LearningCrad)

    @Query("SELECT * FROM LearningCrad")
    suspend fun getAllLearningCrad(): List<LearningCrad>

    @Insert
    suspend fun insertVLCard(vararg card: VisualLearningCard)

    @Delete
    suspend fun deleteVLCrad(vararg card: VisualLearningCard)

    @Update
    suspend fun changeVLCrad(vararg card: VisualLearningCard)

    @Query("SELECT * FROM VisualLearningCard")
    suspend fun getAllVLCrad(): List<VisualLearningCard>

    @Insert
    suspend fun insertALCard(vararg card: AudioLearningCard)

    @Delete
    suspend fun deleteALCrad(vararg card: AudioLearningCard)

    @Update
    suspend fun changeALCrad(vararg card: AudioLearningCard)

    @Query("SELECT * FROM AudioLearningCard")
    suspend fun getAllALCrad(): List<AudioLearningCard>
}
