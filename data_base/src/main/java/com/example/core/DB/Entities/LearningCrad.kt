package com.example.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LearningCrad")
class LearningCrad(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "themeId")
    val themeId: Int,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "answers")
    val answers: List<Answer>,
    @ColumnInfo(name = "themeType")
    val themeType: Int,
    @ColumnInfo(name = "dateCreation")
    val dateCreation: String,
    @ColumnInfo(name = "description")
    val description: String,

)
