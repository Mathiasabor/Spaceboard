package com.example.services

import com.example.database.repository.TicketRepository
import com.example.model.Ticket
import com.example.model.callModel.cAllTicket
import com.example.model.callModel.cDeleteTicket
import com.example.model.callModel.cTicket
import com.example.model.callModel.cUpdateTicket
import org.bson.types.ObjectId
import java.util.Date

class Ticketservice {
    private val ticketRepository = TicketRepository()

    suspend fun addTicket(cTicket: cTicket) : Long
    {
        val ticket = Ticket(
            ID = ObjectId().toString(),
            Title = cTicket.name,
            CreatedAt = Date().time.toString()
        )
       return ticketRepository.addticket(cTicket.projectid, cTicket.ticketlistid, ticket)
    }

    suspend fun deleteTicket(cDeleteTicket: cDeleteTicket) : Long
    {
      return ticketRepository.deleteticket(cDeleteTicket.projectid, cDeleteTicket.ticketlistid, cDeleteTicket.ticketid)

    }

    suspend fun updateTicket(cUpdateTicket: cUpdateTicket) : Long
    {
       return ticketRepository.updateticket(cUpdateTicket.projectid, cUpdateTicket.ticketlistid,cUpdateTicket.tickettoupdate)

    }

    suspend fun getAllTicket(cAllTicket: cAllTicket) : List<Ticket>
    {
       return ticketRepository.getAllTicket(cAllTicket.projectid, cAllTicket.ticketlistid)

    }

    suspend fun moveTicket()
    {
       // ticketRepository.movetiket()
        TODO("Not yet implemented")
    }
}