package com.example.database.repository

import com.example.database.connection.Connection
import com.example.model.Project
import com.example.model.Ticketlist
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import kotlinx.coroutines.flow.toList
import org.bson.conversions.Bson

interface TicketlistInterface {
   suspend fun addticketlist(projectid : String,ticketlist: Ticketlist) : String
   suspend fun deleteticketlist(projectid : String,ticketlistid : String) : Long
   suspend fun updateticketlist(projectid : String,ticketlisttoupdate : Ticketlist) : Long
   suspend fun getAllTicketlist(projectid : String,) : List<Ticketlist>
}

class TicketlistRepository : TicketlistInterface{
    lateinit var query : Bson
    lateinit var updates: Bson
    lateinit var options : UpdateOptions
    val database = Connection.getDatabase()
    val collection = database.getCollection<Project>(collectionName = "project")

    override suspend fun addticketlist(projectid : String,ticketlist: Ticketlist): String {

        query = Filters.eq(Project::Id.name, projectid)
        updates = Updates.combine(
            Updates.addToSet(Project::Ticketlists.name, ticketlist)

        )
        options = UpdateOptions().upsert(true)
        collection.updateOne(query, updates, options).also {
            return it.toString()
        }
    }

    override suspend fun deleteticketlist(projectid : String,ticketlistid: String): Long {
        query = Filters.eq(Project::Id.name, projectid)

        var ticketlists = collection.find().toList().find { it.Id == projectid }?.Ticketlists

         ticketlists?.find { it.Id == ticketlistid }.also {
             ticketlists?.remove(it)
         }


        updates = Updates.combine(

            Updates.set(Project::Ticketlists.name,ticketlists),
        )
        options = UpdateOptions().upsert(true)
        collection.updateOne(query, updates, options).also {
            return it.modifiedCount
        }

    }

    override suspend fun updateticketlist(projectid : String,ticketlisttoupdate: Ticketlist): Long {
        query = Filters.eq(Project::Id.name, projectid)

        var ticketlists = collection.find().toList().find { it.Id == projectid }?.Ticketlists

        ticketlists?.find { it.Id == ticketlisttoupdate.Id }.also {

            ticketlists?.set(ticketlists.indexOf(it), ticketlisttoupdate)
        }


        updates = Updates.combine(

            Updates.set(Project::Ticketlists.name,ticketlists),
        )
        options = UpdateOptions().upsert(true)
        collection.updateOne(query, updates, options).also {
            return it.modifiedCount
        }

    }

    override suspend fun getAllTicketlist(projectid : String): List<Ticketlist> {
        return collection.find().toList().find { it.Id == projectid }?.Ticketlists as List<Ticketlist>


    }

}