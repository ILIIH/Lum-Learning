package com.example.core.DB

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.DB.Entities.GameResults
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GamesDatabaseTest {
    private lateinit var gameDatabase: GamesDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        gameDatabase = Room.inMemoryDatabaseBuilder(context, GamesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        gameDatabase.close()
    }

    @Test
    fun testInsertAndGetGameResult() = runBlocking {
        val gameResult = GameResults(
            id = 0,
            K = 1.0,
            D = 0.0,
            Ch = 11.0,
            T = 90.0,
            Tw1 = 87.1,
            Tw2 = 90.8,
            CurrentDay = 1,
            learningMethodId = 1,
            themeId = 1,
            date = "20.07.2000",
        )
        gameDatabase.gamesDAO().insertGameResult(gameResult)
        val insertedGame = gameDatabase.gamesDAO().getAllGameResult()
        assertNotNull(insertedGame)
        assertEquals(1, insertedGame.size)
        assertEquals(insertedGame.first().themeId, gameResult.themeId)
    }


    @Test
    fun testDeleteUser() = runBlocking {
        val gameResult = GameResults(
            id = 990,
            K = 1.0,
            D = 0.0,
            Ch = 11.0,
            T = 90.0,
            Tw1 = 87.1,
            Tw2 = 90.8,
            CurrentDay = 1,
            learningMethodId = 1,
            themeId = 1,
            date = "20.07.2000",
        )
        gameDatabase.gamesDAO().insertGameResult(gameResult)
        gameDatabase.gamesDAO().deleteGameResult(gameResult)
        val insertedGames = gameDatabase.gamesDAO().getAllGameResult().filter { it.id == 990 }
        assertEquals(0, insertedGames.size)
    }
}
