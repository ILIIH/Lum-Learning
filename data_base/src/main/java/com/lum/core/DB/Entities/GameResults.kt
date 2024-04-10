package com.lum.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gameResults")
class GameResults(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "K")
    val K: Double = 0.0,
    @ColumnInfo(name = "D")
    val D: Double = 0.0,
    @ColumnInfo(name = "Ch")
    val Ch: Double = 0.0,
    @ColumnInfo(name = "T")
    val T: Double = 0.0,
    @ColumnInfo(name = "Tw1")
    val Tw1: Double = 0.0,
    @ColumnInfo(name = "Tw2")
    val Tw2: Double = 0.0,
    @ColumnInfo(name = "CurrentDay")
    val CurrentDay: Int,
    @ColumnInfo(name = "learningMethodId")
    val learningMethodId: Int,
    @ColumnInfo(name = "themeId")
    val themeId: Int,
    @ColumnInfo(name = "date")
    val date: String,
)
