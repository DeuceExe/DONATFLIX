package com.example.impl.room

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toList(string: String?): List<Int>? {
        return if (string.isNullOrEmpty()) {
            null
        } else {
            string.split(",").mapNotNull { it.toIntOrNull() }
        }
    }
}