package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val Id : String,
    val Projectswithroles : MutableList<Projectwithrole>
)
