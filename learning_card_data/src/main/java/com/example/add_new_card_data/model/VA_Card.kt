package com.example.add_new_card_data.model

data class VA_Card(
    override val id: Int,
    override val themeId: Int,
    override val question: String,
    override val answers: List<Answer>,
    val photo: ByteArray,
    val dateCreation: String,
): Card(id, themeId, question, answers )

