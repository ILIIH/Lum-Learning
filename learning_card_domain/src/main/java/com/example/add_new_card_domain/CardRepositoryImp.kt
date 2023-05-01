package com.example.add_new_card_domain

import android.util.Log
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.AL_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card
import com.example.add_new_card_domain.mapper.toData
import com.example.add_new_card_domain.mapper.toDomain
import com.example.core.DB.ThemeDatabase

class CardRepositoryImp(private val repo: ThemeDatabase) : CardRepository {
    override suspend fun insertCard(card: LearningCardDomain) {
        Log.i("card_logging", "INSERT themeId: ${card.themeId}")
        repo.cardsDAO().insertLearningCrad(card.toData())
    }

    override suspend fun getAllCardByThemeId(id: Int): List<LearningCardDomain> =
        repo.cardsDAO().getAllLearningCrad().map { it.toDomain() }.filter { it.themeId == id }

    override suspend fun editLearningCard(card: LearningCardDomain) {
        repo.cardsDAO().changeLearningCrad(card.toData())
    }

    override suspend fun insertALCard(card: AL_Card) {
        Log.i("card_logging", "INSERT themeId: ${card.themeId}")

        repo.cardsDAO().insertALCard(card.toData())
    }

    override suspend fun getAllALCardByThemeId(id: Int): List<AL_Card> =
        repo.cardsDAO().getAllALCrad().map { it.toDomain() }.filter { it.themeId == id }

    override suspend fun getAllALCard(): List<AL_Card> =
        repo.cardsDAO().getAllALCrad().map { it.toDomain() }

    override suspend fun editALCard(card: AL_Card) {
        repo.cardsDAO().changeALCrad(card.toData())
    }

    override suspend fun insertVACard(card: VA_Card) {
        Log.i("card_logging", "INSERT themeId: ${card.themeId}")

        repo.cardsDAO().insertVLCard(card.toData())
    }

    override suspend fun getAllVACardByThemeId(id: Int): List<VA_Card> =
        repo.cardsDAO().getAllVLCrad().map { it.toDomain() }.filter { it.themeId == id }

    override suspend fun editVACard(card: VA_Card) {
        repo.cardsDAO().changeVLCrad(card.toData())
    }
}
