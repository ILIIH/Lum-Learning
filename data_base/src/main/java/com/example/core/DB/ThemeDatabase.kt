package com.example.core.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.DB.Convertors.ListAnswerConverters
import com.example.core.DB.Convertors.ListConverters
import com.example.core.DB.Convertors.ListPairConverters
import com.example.core.DB.DAO.CardsDAO
import com.example.core.DB.DAO.ThemeDAO
import com.example.core.DB.Entities.*

@TypeConverters(ListConverters::class, ListAnswerConverters::class)
@Database(
    entities = [
        ThemeEntity::class,
        LearningMethod::class,
        LearningCrad::class,
        AudioLearningCard::class,
        VisualLearningCard::class,
    ],
    version = 9,
)
abstract class ThemeDatabase : RoomDatabase() {
    abstract fun themeDao(): ThemeDAO
    abstract fun cardsDAO(): CardsDAO
}

