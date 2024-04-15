package com.lum.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AudioLearningCard")
class AudioLearningCard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "themeId")
    val themeId: Int,
    @ColumnInfo(name = "audioFileId")
    val audioFileId: Int,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "answers")
    val answers: List<com.lum.core.DB.Entities.Answer>,
    @ColumnInfo(name = "dateCreation")
    val dateCreation: String,
)
