package com.example.about_theme_domain

import com.example.about_theme_data.ThemeMetric
import com.example.about_theme_data.ThemeMetricRepo
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(
    MockitoJUnitRunner::class,
)
class GetThemeMetricImlTest {

    var repo = Mockito.mock(ThemeMetricRepo::class.java)

    @Test
    fun addition_isCorrect() = runBlocking {
        val themeMetric = ThemeMetric(
            AverageRA = 59.8,
            AverageLastMonthRA = 31.0,
            AverageThisMonthRA = 28.0,
            title = "TestTheme",
            imageUri = "URI",
            yearExperience = 1,
            themeImportance = "Test",
            themeType = "Test",
        )

        Mockito.`when`(repo.getThemeMetric(ArgumentMatchers.anyInt()))
            .thenReturn(
                themeMetric,
            )

        val useCase = GetThemeMetricIml(repo)

        TestCase.assertEquals(
            "Wrong theme metric",
            useCase.execute(1),
            themeMetric,
        )
    }

}
