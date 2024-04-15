package com.lum.add_new_card_domain.mapper

import com.lum.add_new_card_data.model.CardStats
import com.lum.add_new_card_data.model.SA_Card
import com.lum.add_new_card_data.model.LearningCardDomain
import com.lum.add_new_card_data.model.VA_Card
import com.lum.core.DB.Entities.AudioLearningCard
import com.lum.core.DB.Entities.LearningCrad
import com.lum.core.DB.Entities.VisualLearningCard

fun CardStats.toDbEntity() = com.lum.core.DB.Entities.CardStats(
    id = 0,
    cardID  = cardID,
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    repetitionAmount = repetitionAmount,
    lastUpdateNumber = lastMonthUpdateNumber,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber
)
fun LearningCardDomain.toDataWithSaveId() = LearningCrad(
    id = id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.lum.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    themeType = themeType,
    dateCreation = dateCreation,
    description = description,
)
fun LearningCardDomain.toData() = LearningCrad(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.lum.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    themeType = themeType,
    dateCreation = dateCreation,
    description = description,
)

fun LearningCrad.toDomain() = LearningCardDomain(
    id = id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.lum.add_new_card_data.model.Answer(it.answer, it.description, it.correct)
    },
    themeType = themeType,
    dateCreation = dateCreation,
    description = description,
)

fun SA_Card.toData() = AudioLearningCard(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.lum.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    dateCreation = dateCreation,
    audioFileId = audioFileId
)

fun SA_Card.toDataWithId() = AudioLearningCard(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.lum.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    dateCreation = dateCreation,
    audioFileId = audioFileId
)
fun AudioLearningCard.toDomain() = SA_Card(
    id = id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.lum.add_new_card_data.model.Answer(it.answer, it.description, it.correct)
    },
    dateCreation = dateCreation,
    audioFileId = audioFileId
)

fun VA_Card.toData() = VisualLearningCard(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.lum.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    photo = photo,
    dateCreation = dateCreation
)

fun VA_Card.toDataWithId() = VisualLearningCard(
    id = id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.lum.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    photo = photo,
    dateCreation = dateCreation
)

fun VisualLearningCard.toDomain() = VA_Card(
    id = id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.lum.add_new_card_data.model.Answer(it.answer, it.description, it.correct)
    },
    photo = photo,
    dateCreation = dateCreation
)
fun CardStats.toData() = com.lum.core.DB.Entities.CardStats(
    id = id,
    RACurrentMonth =  RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA =  AverageRA,
    cardID = cardID,
    repetitionAmount = repetitionAmount,
    lastUpdateNumber = lastMonthUpdateNumber,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber
)
fun com.lum.core.DB.Entities.CardStats.toDomain() = CardStats(
    id = id,
    RACurrentMonth =  RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA =  AverageRA,
    cardID = cardID,
    repetitionAmount = repetitionAmount,
    lastMonthUpdateNumber = lastUpdateNumber,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber
)