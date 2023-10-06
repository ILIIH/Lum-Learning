package com.example.add_new_card_data

import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card

interface CardRepository {
    suspend fun insertCard(card: LearningCardDomain)
    suspend fun getAllCardByThemeId(id: Int): List<LearningCardDomain>
    suspend fun editLearningCard(card: LearningCardDomain)
    suspend fun deleteLearningCard(id: Int)

    suspend fun insertALCard(card: SA_Card)
    suspend fun getAllALCardByThemeId(id: Int): List<SA_Card>
    suspend fun getAllALCard(): List<SA_Card>
    suspend fun editALCard(card: SA_Card)
    suspend fun deleteALCard(id: Int)


    suspend fun insertVACard(card: VA_Card)
    suspend fun getAllVACardByThemeId(id: Int): List<VA_Card>
    suspend fun editVACard(card: VA_Card)
    suspend fun deleteVACard(id: Int)

}
