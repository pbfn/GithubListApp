package com.pedro_bruno.githublistapp.data_remote.model.response

import com.google.gson.annotations.SerializedName

data class GistResponse(
    @SerializedName("url")
    val url: String,
    @SerializedName("forks_url")
    val forks_url: String,
    @SerializedName("commits_url")
    val commits_url: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("node_id")
    val node_id: String,
    @SerializedName("git_pull_url")
    val git_pull_url: String,
    @SerializedName("git_push_url")
    val git_push_url: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("owner")
    val owner: OwnerResponse,
    @SerializedName("files")
    val files:Map<String,TypeResponse>
)