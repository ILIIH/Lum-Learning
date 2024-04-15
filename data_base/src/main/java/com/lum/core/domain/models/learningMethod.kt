package com.lum.core.domain.models

import com.lum.core.DB.Entities.LearningMethod

data class learningMethod(
    val mnemoType: Int,
    val spacedRepetitionFactor: Double,
    val timeLearningInterval: Int,
    val learningDay: Int,
    val themeType: Int,
)

fun learningMethod.toDayPrediction(): DoubleArray {
    return when (this.learningDay) {
        1 -> doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        2 -> doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        3 -> doubleArrayOf(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0)
        4 -> doubleArrayOf(0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0)
        5 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0)
        6 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0)
        7 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
        else -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
    }
}

fun learningMethod.toTimeInterval(): DoubleArray {
    return when (this.learningDay) {
        1 -> doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        2 -> doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        3 -> doubleArrayOf(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        4 -> doubleArrayOf(0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0)
        5 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0)
        6 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0)
        7 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0)
        8 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
        else -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0)
    }
}

fun learningMethod.toSpacedFactorArr(): DoubleArray {
    return when (this.spacedRepetitionFactor) {
        1.0 -> doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0)
        1.2 -> doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0)
        1.4 -> doubleArrayOf(0.0, 0.0, 1.0, 0.0, 0.0)
        1.8 -> doubleArrayOf(0.0, 0.0, 0.0, 1.0, 0.0)
        2.0 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 1.0)
        else -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0)
    }
}

fun learningMethod.toThemeTypeArr(): DoubleArray {
    return when (this.themeType) {
        1 -> doubleArrayOf(1.0, 0.0, 0.0)
        2 -> doubleArrayOf(0.0, 1.0, 0.0)
        3 -> doubleArrayOf(0.0, 0.0, 1.0)
        else -> doubleArrayOf(0.0, 0.0, 1.0)
    }
}

fun learningMethod.toMnemoTypeArr(): DoubleArray {
    return when (this.mnemoType) {
        1 -> doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        2 -> doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0, 0.0)
        3 -> doubleArrayOf(0.0, 0.0, 1.0, 0.0, 0.0, 0.0)
        4 -> doubleArrayOf(0.0, 0.0, 0.0, 1.0, 0.0, 0.0)
        5 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 1.0, 0.0)
        6 -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
        else -> doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
    }
}

fun learningMethod.toData() = LearningMethod(
    mnemoType = mnemoType,
    spacedRepetitionFactor = spacedRepetitionFactor,
    timeLearningInterval = timeLearningInterval,
    learningDay = learningDay,
    themeType = themeType,
)

fun LearningMethod.toDomain() = learningMethod(
    mnemoType,
    spacedRepetitionFactor,
    timeLearningInterval,
    learningDay,
    themeType,
)
