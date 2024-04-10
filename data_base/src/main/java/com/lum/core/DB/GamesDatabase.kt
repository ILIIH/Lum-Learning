package com.lum.core.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lum.core.DB.Convertors.ListConverters
import com.lum.core.DB.Convertors.ListPairConverters
import com.lum.core.DB.DAO.GamesDAO
import com.lum.core.DB.DAO.LearningMethodDAO
import com.lum.core.DB.Entities.GameResults
import com.lum.core.DB.Entities.LearningMethod

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


