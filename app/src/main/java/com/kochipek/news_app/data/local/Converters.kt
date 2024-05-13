package com.kochipek.news_app.data.local

import androidx.room.TypeConverter
import com.kochipek.news_app.data.model.Source

class Converters {
    @TypeConverter
    fun fromList(source: Source): String {
        return source.name.toString()
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }

}