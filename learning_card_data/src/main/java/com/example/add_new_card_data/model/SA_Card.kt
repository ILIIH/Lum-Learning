package com.example.add_new_card_data.model

data class SA_Card(
    val id: Int = 0,
    val audioFileId: Int,
    val themeId: Int,
    val question: String,
    val answers: List<Answer>,
    val dateCreation: String,
)
