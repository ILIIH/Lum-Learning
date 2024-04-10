package com.lum.core.DB.Convertors

import androidx.room.TypeConverter
import com.lum.core.DB.Entities.Answer
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

object ListAnswerConverters {
    @TypeConverter
    fun fromString(value: String?): List<Answer> {
        val listType: Type = object : TypeToken<List<Answer>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Answer>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
