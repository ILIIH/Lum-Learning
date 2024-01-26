package com.example.add_new_card_data.model

data class SA_Card(
    val audioFileId: Int,
    val themeId: Int,
    val question: String,
    val answers: List<Answer>,
    val RACurrentMonth: Double,
    val RALastMonth: Double,
    val AverageRA: Double,
    val dateCreation: String,
    val repetitionAmount: Int,
    val lastMonthUpdateNumber: Int,
    val lastMonthRepetitionNumber: Int,

)

fun SA_Card.changeRA(thisGameResult: Boolean, currentMonth: Int): SA_Card {
    val averageRA =
        if (thisGameResult) {
            (1 + (this.AverageRA * this.repetitionAmount)) / (this.repetitionAmount + 1)
        } else {
            (this.AverageRA * this.repetitionAmount) / (this.repetitionAmount + 1)
        }

    var lastMonthRA = this.RALastMonth
    var lastMonthRepetitionNumber = this.lastMonthRepetitionNumber

    var curMothRA = 0.0

    if (currentMonth == this.lastMonthUpdateNumber) {
        curMothRA = if (thisGameResult) {
            (1 + (this.RACurrentMonth * this.lastMonthRepetitionNumber)) / (this.lastMonthRepetitionNumber + 1)
        } else {
            (this.AverageRA * this.repetitionAmount) / (this.repetitionAmount + 1)
        }
    } else {
        lastMonthRA = this.RACurrentMonth
        lastMonthRepetitionNumber = 1

        curMothRA = if (thisGameResult) {
            1.0
        } else {
            0.0
        }
    }

    val nexAL = SA_Card(
        themeId = this.themeId,
        question = this.question,
        answers = this.answers,
        RACurrentMonth = curMothRA,
        repetitionAmount = this.repetitionAmount + 1,
        RALastMonth = lastMonthRA,
        lastMonthRepetitionNumber = lastMonthRepetitionNumber,
        AverageRA = averageRA,
        dateCreation = this.dateCreation,
        lastMonthUpdateNumber = currentMonth,
        audioFileId = this.audioFileId
    )

    return nexAL
}
