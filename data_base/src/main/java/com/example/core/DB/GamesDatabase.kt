package com.example.core.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.DB.Convertors.ListConverters
import com.example.core.DB.Convertors.ListPairConverters
import com.example.core.DB.DAO.GamesDAO
import com.example.core.DB.DAO.LearningMethodDAO
import com.example.core.DB.Entities.GameResults
import com.example.core.DB.Entities.LearningMethod

@TypeConverters(ListConverters::class, ListPairConverters::class)
@Database(
    entities = [
        GameResults::class,
        LearningMethod::class,
    ],
    version = 7,
)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun learningMethodDAO(): LearningMethodDAO
    abstract fun gamesDAO(): GamesDAO
}


