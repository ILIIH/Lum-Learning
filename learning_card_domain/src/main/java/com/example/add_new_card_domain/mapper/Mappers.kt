package com.example.add_new_card_domain.mapper

import com.example.add_new_card_data.model.AL_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VL_Card
import com.example.core.DB.Entities.AudioLearningCard
import com.example.core.DB.Entities.LearningCrad
import com.example.core.DB.Entities.VisualLearningCard

fun LearningCardDomain.toData() = LearningCrad(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
)

fun LearningCrad.toDomain() = LearningCardDomain(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.add_new_card_data.model.Answer(it.answer, it.description, it.correct)
    },
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
)

fun AL_Card.toData() = AudioLearningCard(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    audio = audio,
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
)

fun AudioLearningCard.toDomain() = AL_Card(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.add_new_card_data.model.Answer(it.answer, it.description, it.correct)
    },
    audio = audio,
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
)

fun VL_Card.toData() = VisualLearningCard(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    photo = photo,
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
)

fun VisualLearningCard.toDomain() = VL_Card(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.add_new_card_data.model.Answer(it.answer, it.description, it.correct)
    },
    photo = photo,
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
)
