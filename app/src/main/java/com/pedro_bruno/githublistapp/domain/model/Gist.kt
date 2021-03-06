package com.pedro_bruno.githublistapp.domain.model

import java.io.Serializable

data class Gist(
    val id: String,
    val nameOwner: String,
    val photoOwen: String,
    val gistType: List<TypeGist>,
    var checked: Boolean = false
) : Serializable
