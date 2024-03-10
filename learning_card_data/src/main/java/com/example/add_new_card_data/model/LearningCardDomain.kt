package com.example.add_new_card_data.model

data class LearningCardDomain(
    val Id: Int,
    val themeId: Int,
    val question: String,
    val answers: List<Answer>,
    val themeType: Int,
    val dateCreation: String,
    val discription: String,
)

