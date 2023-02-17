package com.example.core.DB.DAO
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.core.DB.Entities.ImageMemoryCard
import com.example.core.DB.Entities.TextMemoryCard
import com.example.core.DB.Entities.ThemeEntity

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

    @Query("SELECT * FROM textMemoryCard WHERE memoTextCardID == :cardId")
    suspend fun getTextMemoryCardById(cardId: Int): TextMemoryCard

    @Query("SELECT * FROM imageMemoryCard WHERE memoPhotoCardID == :cardId")
    suspend fun getImageMemoryCardById(cardId: Int): ImageMemoryCard
}
