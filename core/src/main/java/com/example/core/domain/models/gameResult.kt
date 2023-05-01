package com.example.core.domain.models

import com.example.core.DB.Entities.GameResults

data class gameResult(
    val K: Double,
    val D: Double,
    val Ch: Double,
    val T: Double,
    val Tw1: Double,
    val Tw2: Double,
    val CurrentDay: Int,
    val learningMethodId: Int,
    val themeId: Int,
    val date: String,
)

fun gameResult.toData() = GameResults(
    K = K,
    D = D,
    Ch = Ch,
    T = T,
    Tw1 = Tw1,
    Tw2 = Tw2,
    CurrentDay = CurrentDay,
    learningMethodId = learningMethodId,
    themeId = themeId,
    date = date,
)

fun gameResult.toDoubleArr() =
    doubleArrayOf(this.K, this.D, this.Ch, this.T, this.Tw1, this.Tw2)

fun GameResults.toDomain() = gameResult(
    K = K,
    D = D,
    Ch = Ch,
    T = T,
    Tw1 = Tw1,
    Tw2 = Tw2,
    CurrentDay = CurrentDay,
    learningMethodId = learningMethodId,
    themeId = themeId,
    date = date,
)
