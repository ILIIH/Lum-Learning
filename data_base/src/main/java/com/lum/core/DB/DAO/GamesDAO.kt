package com.lum.core.DB.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lum.core.DB.Entities.GameResults

@Dao
interface GamesDAO {
    @Insert
    suspend fun insertGameResult(vararg result: GameResults)

    @Delete
    suspend fun deleteGameResult(vararg result: GameResults)

    @Query("SELECT * FROM gameResults")
    suspend fun getAllGameResult(): List<GameResults>
}
