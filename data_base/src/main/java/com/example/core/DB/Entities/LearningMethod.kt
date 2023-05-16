package com.example.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learningMethod")
class LearningMethod(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "mnemoType")
    val mnemoType: Int,
    @ColumnInfo(name = "spacedRepetitionFactor")
    val spacedRepetitionFactor: Double,
    @ColumnInfo(name = "timeLearningInterval")
    val timeLearningInterval: Int,
    @ColumnInfo(name = "learningDay")
    val learningDay: Int,
    @ColumnInfo(name = "themeType")
    val themeType: Int,
)


