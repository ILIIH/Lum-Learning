package com.example.add_new_card_data.model

data class LearningCardDomain(
    val themeId: Int,
    val question: String,
    val answers: List<Answer>,
    val RACurrentMonth: Double,
    val RALastMonth: Double,
    val AverageRA: Double,
    val themeType: Int,

)
