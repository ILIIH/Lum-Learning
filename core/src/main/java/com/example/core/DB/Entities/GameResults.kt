package com.example.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gameResults")
class GameResults(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "K")
    val K: Double,
    @ColumnInfo(name = "D")
    val D: Double,
    @ColumnInfo(name = "Ch")
    val Ch: Double,
    @ColumnInfo(name = "T")
    val T: Double,
    @ColumnInfo(name = "Tw1")
    val Tw1: Double,
    @ColumnInfo(name = "Tw2")
    val Tw2: Double,
    @ColumnInfo(name = "CurrentDay")
    val CurrentDay: Int,
    @ColumnInfo(name = "learningMethodId")
    val learningMethodId: Int
)
