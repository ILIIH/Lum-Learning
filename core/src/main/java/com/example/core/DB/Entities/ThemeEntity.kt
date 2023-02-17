package com.example.core.DB.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "themeEntity")
class ThemeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "themeName") val themeName: String?,
    @ColumnInfo(name = "photoThemeURI") val photoThemeURI: String?,
    @ColumnInfo(name = "yearExperience") val yearExperience: Int,
    @ColumnInfo(name = "themeImportance") val themeImportance: String,
    @ColumnInfo(name = "themeTesis") val themeType: String,
    @ColumnInfo(name = "memoPhotoCardIDs") val memoPhotoCardIDs: List<Int>?,
    @ColumnInfo(name = "memoTextCardIDs") val memoTextCardIDs: List<Int>?

)
