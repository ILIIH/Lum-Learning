package com.example.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TextMemoryCard(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "MemoryCardName") val memoryCardName: String?,
    @ColumnInfo(name = "Assosiation") val assosiation: List<Pair<String, String>>?,
    @ColumnInfo(name = "Answer") val answer: String,
    @ColumnInfo(name = "Question") val question: String,
    @ColumnInfo(name = "RAPersentage") val RAPersentage: Double, // RA - Right Answes
    @ColumnInfo(name = "RAMounthIncreases") val RAMounthIncreases: Double,
    @ColumnInfo(name = "RepeatCount") val repeatCount: Double, // RA - Right Answes
    @ColumnInfo(name = "memoTextCardID") val memoTextCardID: Int?
    )
