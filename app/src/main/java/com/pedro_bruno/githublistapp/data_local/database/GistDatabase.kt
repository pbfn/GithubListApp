package com.pedro_bruno.githublistapp.data_local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pedro_bruno.githublistapp.data_local.model.GistDataLocal

@Database(entities = [GistDataLocal::class], version = 1)
abstract class GistDatabase : RoomDatabase() {

    abstract fun gistDao(): GistDao

}