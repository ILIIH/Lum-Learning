package com.example.core.DB.Convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object ListConverters {
    @TypeConverter
    fun fromString(value: String?): List<Int>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Int>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

object ListPairConverters {
    @TypeConverter
    fun fromString(value: String?): List<Pair<String, String>> {
        val listType: Type = object : TypeToken<List<Pair<String, String>>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Pair<String, String>>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
