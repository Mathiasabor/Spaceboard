package com.example.database.repository

import com.example.database.connection.Connection
import com.example.model.Project
import com.example.model.Ticket
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import kotlinx.coroutines.flow.toList
import org.bson.conversions.Bson

interface TicketInterface {

    suspend fun addticket(projectid : String, ticketlistid : String, ticket: Ticket): Long
   suspend fun deleteticket(projectid : String, ticketlistid : String,ticketid : String) : Long
   suspend fun movetiket(projectid : String, ticketlistInitialid : String, ticketlistFinalid : String, ticketid : String) : String
   suspend fun updateticket(projectid : String, ticketlistid : String, tickettoupdate : Ticket) : Long
   suspend fun getAllTicket(projectid : String, ticketlistid : String) : List<Ticket>

}

class TicketRepository : TicketInterface {
    lateinit var query : Bson
    lateinit var updates: Bson
    lateinit var options : UpdateOptions
    val database = Connection.getDatabase()
    val collection = database.getCollection<Project>(collectionName = "project")

    override suspend fun addticket(projectid: String, ticketlistid: String, ticket: Ticket): Long {

        query = Filters.eq(Project::Id.name, projectid)

        var ticketlists = collection.find().toList().find { it.Id == projectid }?.Ticketlists

        ticketlists?.find { it.Id == ticketlistid }.run {
            this?.Tickets?.add(ticket)

        }

        updates = Updates.combine(

            Updates.set(Project::Ticketlists.name,ticketlists),
        )
        options = UpdateOptions().upsert(true)
        collection.updateOne(query, updates, options).also {
            return it.modifiedCount
        }

    }

    override suspend fun deleteticket(projectid: String, ticketlistid: String,ticketid : String): Long {
        query = Filters.eq(Project::Id.name, projectid)

        var ticketlists = collection.find().toList().find { it.Id == projectid }?.Ticketlists

        ticketlists?.find { it.Id == ticketlistid }.run {
            this?.Tickets?.find { it.ID == ticketid }.also {
                this?.Tickets?.remove(it)
            }

        }

        updates = Updates.combine(

            Updates.set(Project::Ticketlists.name,ticketlists),
        )
        options = UpdateOptions().upsert(true)
        collection.updateOne(query, updates, options).also {
            return it.modifiedCount
        }

    }

    override suspend fun movetiket(projectid: String, ticketlistInitialid: String, ticketlistFinalid: String,ticketid : String): String {


        TODO("Not yet implemented")
    }

    override suspend fun updateticket(projectid: String, ticketlistid: String, tickettoupdate: Ticket): Long {

        query = Filters.eq(Project::Id.name, projectid)

        var ticketlists = collection.find().toList().find { it.Id == projectid }?.Ticketlists

        ticketlists?.find { it.Id == ticketlistid }.run {
            this?.Tickets?.find { it.ID == tickettoupdate.ID }.also {
                this?.Tickets?.set(this.Tickets.indexOf(it), tickettoupdate)
            }

        }

        updates = Updates.combine(

            Updates.set(Project::Ticketlists.name,ticketlists),
        )
        options = UpdateOptions().upsert(true)
        collection.updateOne(query, updates, options).also {
            return it.modifiedCount
        }
    }

    override suspend fun getAllTicket(projectid: String, ticketlistid: String): List<Ticket> {

      return  collection.find().toList().find { it.Id == projectid }?.Ticketlists?.find { it.Id == ticketlistid }?.Tickets as List<Ticket>


    }
}



