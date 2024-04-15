package com.lum.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VisualLearningCard")
class VisualLearningCard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "themeId")
    val themeId: Int,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "answers")
    val answers: List<com.lum.core.DB.Entities.Answer>,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val photo: ByteArray,
    @ColumnInfo(name = "dateCreation")
    val dateCreation: String,
)
