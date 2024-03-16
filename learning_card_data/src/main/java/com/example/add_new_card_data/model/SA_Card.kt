package com.example.add_new_card_data.model

data class SA_Card(
    override val id: Int,
    override val themeId: Int,
    override val question: String,
    override val answers: List<Answer>,
    val audioFileId: Int,
    val dateCreation: String,
): Card(id, themeId, question, answers )

