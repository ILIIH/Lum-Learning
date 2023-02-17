package com.example.core.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.DB.Convertors.ListConverters
import com.example.core.DB.Convertors.ListPairConverters
import com.example.core.DB.DAO.ThemeDAO
import com.example.core.DB.Entities.ImageMemoryCard
import com.example.core.DB.Entities.TextMemoryCard
import com.example.core.DB.Entities.ThemeEntity

@TypeConverters(ListConverters::class, ListPairConverters::class)
@Database(entities = [ThemeEntity::class, ImageMemoryCard::class, TextMemoryCard::class], version = 3)
abstract class ThemeDatabace : RoomDatabase() {
    abstract fun themeDao(): ThemeDAO
}