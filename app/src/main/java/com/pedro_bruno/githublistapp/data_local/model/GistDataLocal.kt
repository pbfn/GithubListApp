package com.pedro_bruno.githublistapp.data_local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gists")
data class GistDataLocal(
    @PrimaryKey
    val id: String,
    val nameOwner: String,
    val photoOwen: String,
    val gistType: String,
)