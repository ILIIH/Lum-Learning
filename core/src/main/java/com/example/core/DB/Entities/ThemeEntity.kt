package com.example.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "themeEntity")
class ThemeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "themeName") val themeName: String?,
    @ColumnInfo(name = "photoThemeURI") val photoThemeURI: String?,
    @ColumnInfo(name = "memoPhotoCardID") val memoPhotoCardID: Int?,
    @ColumnInfo(name = "memoTextCardID") val memoTextCardID: Int?

    )