package com.example.add_new_card_data.model

data class AL_Card(
    val themeId: Int,
    val question: String,
    val answers: List<Answer>,
    val audio: ByteArray,
    val RACurrentMonth: Double,
    val RALastMonth: Double,
    val AverageRA: Double,
)
