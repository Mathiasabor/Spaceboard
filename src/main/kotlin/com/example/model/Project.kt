package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Project(
    val Id : String,
    val Name : String,
    val Author : Author,
    val Members : MutableList<Member> = mutableListOf(),
    val Ticketlists : MutableList<Ticketlist> = mutableListOf()
)
