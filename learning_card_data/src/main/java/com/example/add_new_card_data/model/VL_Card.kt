package com.example.add_new_card_data.model

data class VL_Card(
    val themeId: Int,
    val question: String,
    val answers: List<Answer>,
    val photo: ByteArray,
    val RACurrentMonth: Double,
    val RALastMonth: Double,
    val AverageRA: Double,
)
