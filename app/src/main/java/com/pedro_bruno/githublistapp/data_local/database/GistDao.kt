package com.pedro_bruno.githublistapp.data_local.database

import androidx.room.*
import com.pedro_bruno.githublistapp.data_local.model.GistDataLocal

@Dao
interface GistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun favoriteGist(gist: GistDataLocal)

    @Delete
    fun removeGist(gist: GistDataLocal)

    @Query("SELECT * FROM gists WHERE nameOwner LIKE  '%' || :querySearch || '%' ORDER BY id")
    fun fetchListFavorites(querySearch: String): List<GistDataLocal>

}