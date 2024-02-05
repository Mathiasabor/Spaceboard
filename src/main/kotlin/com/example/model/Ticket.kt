package com.example.model

import kotlinx.serialization.Serializable
import java.util.*
@Serializable
data class Ticket(
    val ID : String,
    var Title : String,
    var Description: String ="",
    var StartDate : String="",
    var EndDate : String="",
    var Type : String ="",
    var Priority : String="",
    val CreatedAt : String,
    var UpdatedAt : String=""
)
