package com.lum.add_new_card_data.model


open class Card(
    open val id: Int,
    open val themeId: Int,
    open val question: String,
    open val answers: List<Answer>,
)
