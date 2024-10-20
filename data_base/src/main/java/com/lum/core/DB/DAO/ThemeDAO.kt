package com.lum.core.DB.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lum.core.DB.Entities.ThemeEntity

@Dao
interface ThemeDAO {
    @Insert
    suspend fun insertTheme(vararg theme: ThemeEntity)

    @Delete
    suspend fun deleteTheme(vararg theme: ThemeEntity)

    @Query("SELECT * FROM themeEntity")
    suspend fun getAllTheme(): List<ThemeEntity>

    @Query("SELECT * FROM themeEntity WHERE id == :themeId")
    suspend fun getThemeById(themeId: Int): ThemeEntity

}

