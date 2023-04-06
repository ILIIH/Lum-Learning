package com.example.add_new_card_data

import com.example.add_new_card_data.model.AL_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VL_Card

interface CardRepository {
    suspend fun incestCard(card: LearningCardDomain)
    suspend fun getAllCardByThemeId(id: Int): List<LearningCardDomain>
    suspend fun editLearningCard(card: LearningCardDomain)

    suspend fun incestALCard(card: AL_Card)
    suspend fun getAllALCardByThemeId(id: Int): List<AL_Card>
    suspend fun getAllALCard(): List<AL_Card>

    suspend fun editALCard(card: AL_Card)

    suspend fun incestVACard(card: VL_Card)
    suspend fun getAllVACardByThemeId(id: Int): List<VL_Card>
    suspend fun editVACard(card: VL_Card)
}
