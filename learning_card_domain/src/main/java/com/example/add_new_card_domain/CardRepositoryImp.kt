package com.example.add_new_card_domain

import android.util.Log
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.CardStats
import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card
import com.example.add_new_card_domain.mapper.toData
import com.example.add_new_card_domain.mapper.toDataWithId
import com.example.add_new_card_domain.mapper.toDataWithSaveId
import com.example.add_new_card_domain.mapper.toDomain
import com.example.core.DB.ThemeDatabase

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
        repo.cardsDAO().changeCardStat(cardStats.toData())
    }

    override suspend fun insertCard(card: LearningCardDomain) {
        repo.cardsDAO().insertLearningCrad(card.toData())
    }

    override suspend fun getAllCardByThemeId(id: Int): List<LearningCardDomain> =
        repo.cardsDAO().getAllLearningCrad().map { it.toDomain() }.filter { it.themeId == id }

    override suspend fun editLearningCard(card: LearningCardDomain) {
        repo.cardsDAO().changeLearningCrad(card.toDataWithSaveId())
    }

    override suspend fun deleteLearningCard(id: Int) {
        repo.cardsDAO().deleteLearningCardById(id)
    }

    override suspend fun insertALCard(card: SA_Card) {
        repo.cardsDAO().insertALCard(card.toData())
    }

    override suspend fun getAllALCardByThemeId(id: Int): List<SA_Card> {
        val cards = repo.cardsDAO().getAllALCrad()
        val cardsAmount = cards.count { it.themeId == id  }
        return if(cardsAmount > 0){
            cards.map { it.toDomain() }.filter { it.themeId == id }
        } else{
            listOf()
        }
    }

    override suspend fun getAllALCard(): List<SA_Card> =
        repo.cardsDAO().getAllALCrad().map { it.toDomain() }

    override suspend fun editALCard(card: SA_Card) {
        repo.cardsDAO().changeALCrad(card.toDataWithId())
    }

    override suspend fun deleteALCard(id: Int) {
        repo.cardsDAO().deleteALCardId(id)
    }

    override suspend fun insertVACard(card: VA_Card) {
        repo.cardsDAO().insertVLCard(card.toData())
    }

    override suspend fun getAllVACardByThemeId(id: Int): List<VA_Card> =
        repo.cardsDAO().getAllVLCrad().map { it.toDomain() }.filter { it.themeId == id }

    override suspend fun editVACard(card: VA_Card) {
        repo.cardsDAO().changeVLCrad(card.toDataWithId())
    }

    override suspend fun deleteVACard(id: Int) {
        repo.cardsDAO().deleteVACardId(id)
    }
}
