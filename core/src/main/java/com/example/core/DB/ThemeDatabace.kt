package com.example.core.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.DB.DAO.ThemeDAO
import com.example.core.DB.Entities.ThemeEntity

@Database(entities = [ThemeEntity::class], version = 1)
abstract class ThemeDatabace : RoomDatabase() {
    abstract fun themeDao(): ThemeDAO
}