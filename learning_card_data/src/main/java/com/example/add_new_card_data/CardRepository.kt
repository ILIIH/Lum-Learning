package com.example.add_new_card_data

import com.example.add_new_card_data.model.AL_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card

interface CardRepository {
    suspend fun insertCard(card: LearningCardDomain)
    suspend fun getAllCardByThemeId(id: Int): List<LearningCardDomain>
    suspend fun editLearningCard(card: LearningCardDomain)

    suspend fun insertALCard(card: AL_Card)
    suspend fun getAllALCardByThemeId(id: Int): List<AL_Card>
    suspend fun getAllALCard(): List<AL_Card>
    suspend fun editALCard(card: AL_Card)

    suspend fun insertVACard(card: VA_Card)
    suspend fun getAllVACardByThemeId(id: Int): List<VA_Card>
    suspend fun editVACard(card: VA_Card)
}
