package com.lum.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CardStats")
class CardStats(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "cardID")
    val cardID: Int,
    @ColumnInfo(name = "RACurrentMonth")
    val RACurrentMonth: Double,
    @ColumnInfo(name = "RALastMonth")
    val RALastMonth: Double,
    @ColumnInfo(name = "AverageRA")
    val AverageRA: Double,
    @ColumnInfo(name = "repetitionAmount")
    val repetitionAmount: Int,
    @ColumnInfo(name = "lastUpdateNumber")
    val lastUpdateNumber: Int,
    @ColumnInfo(name = "lastMonthRepetitionNumber")
    val lastMonthRepetitionNumber: Int
)