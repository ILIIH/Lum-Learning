package com.example.edit_ui.data

enum class CardType {
    LEARNING_CARD,
    VA_CARD,
    SL_CARD,
}
data class Card(val id: Int, val type: CardType, val question: String)
