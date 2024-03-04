package com.example.add_new_card_domain.mapper

import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card
import com.example.core.DB.Entities.AudioLearningCard
import com.example.core.DB.Entities.LearningCrad
import com.example.core.DB.Entities.VisualLearningCard

fun LearningCardDomain.toDataWithSaveId() = LearningCrad(
    id = Id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    themeType = themeType,
    dateCreation = dateCreation,
    description = discription,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber,
    lastUpdateNumber = lastMonthUpdateNumber,
    repetitionAmount = repetitionAmount,
)
fun LearningCardDomain.toData() = LearningCrad(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    themeType = themeType,
    dateCreation = dateCreation,
    description = discription,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber,
    lastUpdateNumber = lastMonthUpdateNumber,
    repetitionAmount = repetitionAmount,
)

fun LearningCrad.toDomain() = LearningCardDomain(
    Id = id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.add_new_card_data.model.Answer(it.answer, it.description, it.correct)
    },
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    themeType = themeType,
    dateCreation = dateCreation,
    discription = description,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber,
    repetitionAmount = repetitionAmount,
    lastMonthUpdateNumber = lastUpdateNumber,
)

fun SA_Card.toData() = AudioLearningCard(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    dateCreation = dateCreation,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber,
    lastUpdateNumber = lastMonthUpdateNumber,
    repetitionAmount = repetitionAmount,
    audioFileId = audioFileId
)

fun SA_Card.toDataWithId() = AudioLearningCard(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    dateCreation = dateCreation,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber,
    lastUpdateNumber = lastMonthUpdateNumber,
    repetitionAmount = repetitionAmount,
    audioFileId = audioFileId
)
fun AudioLearningCard.toDomain() = SA_Card(
    id = id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.add_new_card_data.model.Answer(it.answer, it.description, it.correct)
    },
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    dateCreation = dateCreation,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber,
    repetitionAmount = repetitionAmount,
    lastMonthUpdateNumber = lastUpdateNumber,
    audioFileId = audioFileId

)

fun VA_Card.toData() = VisualLearningCard(
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    photo = photo,
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    dateCreation = dateCreation,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber,
    lastUpdateNumber = lastMonthUpdateNumber,
    repetitionAmount = repetitionAmount,
)

fun VA_Card.toDataWithId() = VisualLearningCard(
    id = Id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.core.DB.Entities.Answer(it.answer, it.description, it.correct)
    },
    photo = photo,
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    dateCreation = dateCreation,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber,
    lastUpdateNumber = lastMonthUpdateNumber,
    repetitionAmount = repetitionAmount,
)

fun VisualLearningCard.toDomain() = VA_Card(
    Id = id,
    themeId = themeId,
    question = question,
    answers = answers.map {
        com.example.add_new_card_data.model.Answer(it.answer, it.description, it.correct)
    },
    photo = photo,
    RACurrentMonth = RACurrentMonth,
    RALastMonth = RALastMonth,
    AverageRA = AverageRA,
    dateCreation = dateCreation,
    lastMonthRepetitionNumber = lastMonthRepetitionNumber,
    repetitionAmount = repetitionAmount,
    lastMonthUpdateNumber = lastUpdateNumber,
)
