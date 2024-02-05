package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val Id : String,
    val name : String
)
