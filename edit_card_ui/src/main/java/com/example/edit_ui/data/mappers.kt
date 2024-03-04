package com.example.edit_ui.data

import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card

fun LearningCardDomain.toCard() = Card(id = this.Id, type = CardType.LEARNING_CARD, question = this.question)

fun VA_Card.toCard() = Card(id = this.Id, type = CardType.VA_CARD, question = this.question)

fun SA_Card.toCard() = Card(id = this.id, type = CardType.SL_CARD, question = this.question)