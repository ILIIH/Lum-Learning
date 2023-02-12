package com.example.core.DB.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.core.DB.Entities.ThemeEntity

@Dao
interface ThemeDAO {
    @Insert
    suspend fun insertTheme(vararg theme: ThemeEntity)

    @Delete
    suspend fun deleteTheme(vararg theme: ThemeEntity)

    @Query("SELECT * FROM themeEntity")
    suspend fun getAll(): List<ThemeEntity>
}
