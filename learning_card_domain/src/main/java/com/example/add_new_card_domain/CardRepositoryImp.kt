package com.example.add_new_card_domain

import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.AL_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VL_Card
import com.example.add_new_card_domain.mapper.toData
import com.example.add_new_card_domain.mapper.toDomain
import com.example.core.DB.ThemeDatabase

class CardRepositoryImp(private val repo: ThemeDatabase) : CardRepository {
    override suspend fun incestCard(card: LearningCardDomain) {
        repo.cardsDAO().insertLearningCrad(card.toData())
    }

    override suspend fun getAllCardByThemeId(id: Int): List<LearningCardDomain> =
        repo.cardsDAO().getAllLearningCrad().map { it.toDomain() }.filter { it.themeId == id }


    override suspend fun editLearningCard(card: LearningCardDomain) {
        repo.cardsDAO().changeLearningCrad(card.toData())
    }

    override suspend fun incestALCard(card: AL_Card){
        repo.cardsDAO().insertALCard(card.toData())
    }

    override suspend fun getAllALCardByThemeId(id: Int): List<AL_Card> =
        repo.cardsDAO().getAllALCrad().map { it.toDomain() }.filter { it.themeId == id }



    override suspend fun editALCard(card: AL_Card) {
        repo.cardsDAO().changeALCrad(card.toData())
    }

    override suspend fun incestVLCard(card: VL_Card) {
        repo.cardsDAO().insertVLCard(card.toData())
    }

    override suspend fun getAllVLCardByThemeId(id: Int): List<VL_Card> =
        repo.cardsDAO().getAllVLCrad().map { it.toDomain() }.filter { it.themeId == id }


    override suspend fun editVLCard(card: VL_Card) {
        repo.cardsDAO().changeVLCrad(card.toData())
    }
}
