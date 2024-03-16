package com.example.add_new_card_data

import com.example.add_new_card_data.model.Card
import com.example.add_new_card_data.model.CardStats
import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card

interface CardRepository {
    suspend fun editCardStat(cardStats: CardStats)
    suspend fun getCardStat(cardId: Int): CardStats?

    suspend fun insertCard(card: Card)
    suspend fun getAllCardByThemeId(id: Int): List<Card>
    suspend fun editCard(card: Card)
    suspend fun deleteCard(id: Int)

}
