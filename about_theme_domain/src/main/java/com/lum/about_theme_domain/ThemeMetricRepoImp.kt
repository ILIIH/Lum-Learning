package com.lum.about_theme_domain

import android.util.Log
import com.lum.about_theme_data.ThemeMetric
import com.lum.about_theme_data.ThemeMetricRepo
import com.lum.core.DB.DAO.CardsDAO
import com.lum.core.DB.DAO.ThemeDAO
import com.lum.core.domain.ThemeType

class ThemeMetricRepoImp(private val cardsDAO: CardsDAO, private val themeDAO: ThemeDAO) :
    ThemeMetricRepo {
    override suspend fun getThemeMetric(id: Int): ThemeMetric {

        var sumCurMonthRA = 0.0
        var sumLastMonthRA = 0.0
        var sumAveraheRA = 0.0

        var cardCount = 0

        val theme = themeDAO.getThemeById(id)

        val AL_Card = cardsDAO.getAllALCrad().filter { it.themeId == id }
        val VL_Card = cardsDAO.getAllVLCrad().filter { it.themeId == id }
        val LearningCard = cardsDAO.getAllLearningCrad().filter { it.themeId == id }

        val cardStats = cardsDAO.getAllCardStats().filter {cardStat ->
            Log.i("theme_info_logging", "cardStat ${cardStat}  " )

            AL_Card.any { it.id == cardStat.cardID } ||
            VL_Card.any { it.id == cardStat.cardID } ||
            LearningCard.any{it.id == cardStat.cardID}
        }

        Log.i("theme_info_logging", "AL_Card ${AL_Card}  " )
        Log.i("theme_info_logging", "VL_Card ${VL_Card}  " )
        Log.i("theme_info_logging", "LearningCard ${LearningCard}  " )

        cardStats.forEach {
            sumCurMonthRA += it.RACurrentMonth
            sumLastMonthRA += it.RALastMonth
            sumAveraheRA += it.AverageRA
            cardCount++
        }
        Log.i("theme_info_logging", "cardStats ${cardStats}  " )


        if (cardCount == 0) {
            return ThemeMetric(
                0.0,
                0.0,
                0.0,
                theme.themeName,
                theme.photoThemeURI,
                theme.yearExperience,
                theme.themeImportance,
                ThemeType.fromInt(theme.themeType).name,
            )
        } else {
            return ThemeMetric(
                sumAveraheRA / cardCount,
                sumLastMonthRA / cardCount,
                sumCurMonthRA / cardCount,
                theme.themeName,
                theme.photoThemeURI,
                theme.yearExperience,
                theme.themeImportance,
                ThemeType.fromInt(theme.themeType).name,

            )
        }
    }
}
