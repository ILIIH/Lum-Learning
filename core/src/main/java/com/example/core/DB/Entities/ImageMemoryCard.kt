package com.example.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imageMemoryCard")
class ImageMemoryCard(
    @ColumnInfo(name = "MemoryCardName") val memoryCardName: String?,
    @ColumnInfo(name = "Association") val association: List<Pair<String, String>>?,
    @ColumnInfo(name = "AnswerImage") val answerImage: String,
    @ColumnInfo(name = "QuestionImage") val questionImage: String,
    @ColumnInfo(name = "RAPercentage") val RAPercentage: Double, // RA - Right Answes
    @ColumnInfo(name = "RALastMonth") val RALastMonth: Double,
    @ColumnInfo(name = "RACurrentMonth") val RACurrentMonth: Double,
    @ColumnInfo(name = "RepeatCount") val repeatCount: Double, // RA - Right Answes
    @PrimaryKey @ColumnInfo(name = "memoPhotoCardID")
    val memoPhotoCardID: Int

)
