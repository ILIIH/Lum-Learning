package com.example.add_new_card_data.model

data class CardStats (
    val id: Int = 0,
    val cardID: Int,
    val RACurrentMonth: Double,
    val RALastMonth: Double,
    val AverageRA: Double,
    val repetitionAmount: Int,
    val lastMonthUpdateNumber: Int,
    val lastMonthRepetitionNumber: Int
)

fun CardStats.changeRA(thisGameResult: Boolean, currentMonth: Int): CardStats {
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

    return CardStats(
        id = this.id,
        cardID = this.cardID,
        RACurrentMonth = curMothRA,
        repetitionAmount = this.repetitionAmount + 1,
        RALastMonth = lastMonthRA,
        lastMonthRepetitionNumber = lastMonthRepetitionNumber,
        AverageRA = averageRA,
        lastMonthUpdateNumber = currentMonth,
    )
}