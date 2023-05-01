package com.example.add_new_card_data.model

data class LearningCardDomain(
    val Id: Int,
    val themeId: Int,
    val question: String,
    val answers: List<Answer>,
    val RACurrentMonth: Double,
    val RALastMonth: Double,
    val AverageRA: Double,
    val themeType: Int,
    val dateCreation: String,
    val discription: String,
    val repetitionAmount: Int,
    val lastMonthUpdateNumber: Int,
    val lastMonthRepetitionNumber: Int,

)

fun LearningCardDomain.changeRA(thisGameResult: Boolean, currentMonth: Int): LearningCardDomain {
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

    return LearningCardDomain(
        Id = this.Id,
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
        discription = this.discription,
        themeType = this.themeType,
    )
}
