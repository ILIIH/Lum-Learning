package com.example.about_theme_domain

import com.example.about_theme_data.ThemeMetric
import com.example.core.DB.DAO.CardsDAO
import com.example.core.DB.DAO.ThemeDAO
import com.example.core.DB.Entities.AudioLearningCard
import com.example.core.DB.Entities.ThemeEntity
import com.example.core.DB.Entities.VisualLearningCard
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(
    MockitoJUnitRunner::class,
)
class ThemeMetricRepoImpTest {

    var themeDAO = mock(ThemeDAO::class.java)
    var cardsDAO = mock(CardsDAO::class.java)

    @Test
    fun addition_isCorrect() = runBlocking {
        val currentTheme = ThemeEntity(
            id = 0,
            themeName = "TestTheme",
            photoThemeURI = "",
            yearExperience = 1,
            themeImportance = "",
            themeType = "",
            photo = byteArrayOf(),
        )

        val trueAnswer = ThemeMetric(
            AverageRA = 59.8,
            AverageLastMonthRA = 31.0,
            AverageThisMonthRA = 28.0,
            title = currentTheme.themeName,
            imageUri = currentTheme.photoThemeURI,
            yearExperience = currentTheme.yearExperience,
            themeImportance = currentTheme.themeImportance,
            themeType = currentTheme.themeType,
        )

        `when`(themeDAO.getThemeById(anyInt())).thenReturn(
            currentTheme,
        )

        `when`(cardsDAO.getAllALCrad()).thenReturn(
            listOf(
                AudioLearningCard(
                    id = 1,
                    themeId = 1,
                    question = "Test question",
                    listOf(),
                    RACurrentMonth = 50.0,
                    RALastMonth = 20.0,
                    AverageRA = 73.0,
                    dateCreation = "",
                ),
                AudioLearningCard(
                    id = 2,
                    themeId = 1,
                    question = "Test question",
                    listOf(),
                    RACurrentMonth = 20.0,
                    RALastMonth = 55.0,
                    AverageRA = 73.0,
                    dateCreation = "",
                ),
                AudioLearningCard(
                    id = 3,
                    themeId = 1,
                    question = "Test question",
                    listOf(),
                    RACurrentMonth = 0.0,
                    RALastMonth = 5.0,
                    AverageRA = 7.0,
                    dateCreation = "",
                ),
            ),
        )

        `when`(cardsDAO.getAllVLCrad()).thenReturn(
            listOf(
                VisualLearningCard(
                    id = 1,
                    themeId = 1,
                    question = "Test question",
                    listOf(),
                    RACurrentMonth = 50.0,
                    RALastMonth = 20.0,
                    AverageRA = 73.0,
                    dateCreation = "",
                    photo = byteArrayOf(),
                ),
                VisualLearningCard(
                    id = 2,
                    themeId = 1,
                    question = "Test question",
                    listOf(),
                    RACurrentMonth = 20.0,
                    RALastMonth = 55.0,
                    AverageRA = 73.0,
                    dateCreation = "",
                    photo = byteArrayOf(),
                ),
            ),
        )

        `when`(cardsDAO.getAllLearningCrad()).thenReturn(
            listOf(),
        )
        val repo = ThemeMetricRepoImp(cardsDAO, themeDAO)

        assertEquals(
            "Wrong theme metric",
            repo.getThemeMetric(1),
            trueAnswer,
        )
    }
}
