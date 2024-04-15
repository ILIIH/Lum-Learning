package com.lum.add_new_card_domain

import android.util.Log
import com.lum.add_new_card_data.CardRepository
import com.lum.add_new_card_data.model.Card
import com.lum.add_new_card_data.model.CardStats
import com.lum.add_new_card_data.model.SA_Card
import com.lum.add_new_card_data.model.LearningCardDomain
import com.lum.add_new_card_data.model.VA_Card
import com.lum.add_new_card_domain.mapper.toData
import com.lum.add_new_card_domain.mapper.toDataWithId
import com.lum.add_new_card_domain.mapper.toDataWithSaveId
import com.lum.add_new_card_domain.mapper.toDbEntity
import com.lum.add_new_card_domain.mapper.toDomain
import com.lum.core.DB.ThemeDatabase

class CardRepositoryImp(private val repo: ThemeDatabase) : CardRepository {
    override suspend fun getCardStat(cardId: Int): CardStats {
        val cards = repo.cardsDAO().getAllCardStats()
        val cardsAmount = cards.count { it.cardID == cardId }
        return if (cardsAmount > 0 ){
            cards.first { it.cardID == cardId }.toDomain()
        } else {
            CardStats(
                cardID =  cardId,
                id =0,
                RACurrentMonth =  0.0,
                RALastMonth =  0.0,
                AverageRA = 0.0,
                repetitionAmount = 0,
                lastMonthRepetitionNumber = 0,
                lastMonthUpdateNumber = 0
            )
        }
    }

    override suspend fun editCardStat(cardStats: CardStats) {
        if( repo.cardsDAO().getAllCardStats().count {  it.cardID == cardStats.cardID } > 0) {
            repo.cardsDAO().changeCardStat(cardStats.toData())
        }
        else {
            repo.cardsDAO().insertCardStat(cardStats.toDbEntity())
        }

    }

    override suspend fun insertCard(card: Card) {
        when(card) {
            is LearningCardDomain -> {repo.cardsDAO().insertLearningCrad(card.toData())}
            is SA_Card -> {repo.cardsDAO().insertALCard(card.toData())}
            is VA_Card -> {repo.cardsDAO().insertVLCard(card.toData())}
        }
    }

    override suspend fun getAllCardByThemeId(id: Int): List<Card>  =
        repo.cardsDAO().getAllLearningCrad().map { it.toDomain() }.filter { it.themeId == id } +
        repo.cardsDAO().getAllALCrad().map { it.toDomain() }.filter { it.themeId == id } +
        repo.cardsDAO().getAllVLCrad().map { it.toDomain() }.filter { it.themeId == id }




    override suspend fun editCard(card: Card) {
        when(card) {
            is LearningCardDomain -> {repo.cardsDAO().changeLearningCrad(card.toData())}
            is SA_Card -> {repo.cardsDAO().changeALCrad(card.toData())}
            is VA_Card -> {repo.cardsDAO().changeVLCrad(card.toData())}
        }
    }

    override suspend fun deleteCard(id: Int) {
        repo.cardsDAO().deleteLearningCardById(id)
        repo.cardsDAO().deleteALCardId(id)
        repo.cardsDAO().deleteVACardId(id)
    }

}
