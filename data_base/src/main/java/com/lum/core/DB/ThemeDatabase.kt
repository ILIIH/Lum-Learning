package com.lum.core.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lum.core.DB.Convertors.ListAnswerConverters
import com.lum.core.DB.Convertors.ListConverters
import com.lum.core.DB.Convertors.ListPairConverters
import com.lum.core.DB.DAO.CardsDAO
import com.lum.core.DB.DAO.ThemeDAO
import com.lum.core.DB.Entities.*

@TypeConverters(ListConverters::class, ListAnswerConverters::class)
@Database(
    entities = [
        ThemeEntity::class,
        LearningMethod::class,
        LearningCrad::class,
        AudioLearningCard::class,
        VisualLearningCard::class,
        CardStats::class
    ],
    version = 11,
)
abstract class ThemeDatabase : RoomDatabase() {
    abstract fun themeDao(): ThemeDAO
    abstract fun cardsDAO(): CardsDAO
}

