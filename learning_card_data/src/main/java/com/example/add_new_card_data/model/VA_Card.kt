package com.example.add_new_card_data.model

data class VA_Card(
    val Id: Int,
    val themeId: Int,
    val question: String,
    val answers: List<Answer>,
    val photo: ByteArray,
    val dateCreation: String,
)
