package com.example.core.domain.models

import com.example.core.DB.Entities.GameResults

data class gameResult(
    val id: Int,
    val K: Double,
    val D: Double,
    val Ch: Double,
    val T: Double,
    val Tw1: Double,
    val Tw2: Double,
    val CurrentDay: Int,
    val learningMethodId: Int
)

fun gameResult.toData() = GameResults(
    id, K, D, Ch, T, Tw1, Tw2, CurrentDay, learningMethodId
)

fun gameResult.toDoubleArr() =
    doubleArrayOf(this.K, this.D, this.Ch, this.T, this.Tw1, this.Tw2)

fun GameResults.toDomain() = gameResult(
    id, K, D, Ch, T, Tw1, Tw2, CurrentDay, learningMethodId
)