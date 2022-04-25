package com.pedro_bruno.githublistapp.data_local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pedro_bruno.githublistapp.domain.model.TypeGist

class Converters {

    @TypeConverter
    fun listTypeToJson(listTypeGist: List<TypeGist>): String {
        return Gson().toJson(listTypeGist)
    }

    @TypeConverter
    fun jsonToListType(value: String): List<TypeGist> {
        val objects = Gson().fromJson(value, Array<TypeGist>::class.java) as Array<TypeGist>
        return objects.toList()
    }

}