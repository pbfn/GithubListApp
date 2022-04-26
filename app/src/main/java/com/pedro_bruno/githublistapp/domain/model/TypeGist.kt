package com.pedro_bruno.githublistapp.domain.model

import java.io.Serializable

data class TypeGist(
    val filename: String,
    val type: String
): Serializable

