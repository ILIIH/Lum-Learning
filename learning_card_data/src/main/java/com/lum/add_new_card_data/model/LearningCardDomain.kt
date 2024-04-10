package com.lum.add_new_card_data.model

data class LearningCardDomain(
    override val id: Int,
    override val themeId: Int,
    override val question: String,
    override val answers: List<Answer>,
    val themeType: Int,
    val dateCreation: String,
    val description: String,
): Card(id, themeId, question, answers )

