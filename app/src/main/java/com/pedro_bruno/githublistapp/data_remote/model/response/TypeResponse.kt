package com.pedro_bruno.githublistapp.data_remote.model.response

import com.google.gson.annotations.SerializedName

data class TypeResponse(
    @SerializedName("filename")
    val filename: String,
    @SerializedName("type")
    val type: String
)
