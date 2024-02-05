package com.example.handlers

import com.example.model.callModel.cAllTicket
import com.example.model.callModel.cDeleteTicket
import com.example.model.callModel.cTicket
import com.example.model.callModel.cUpdateTicket
import com.example.services.Ticketservice
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.Tickethandler(){
    val ticketservice = Ticketservice()
    get("/all") {
       val cAllTicket = call.receive<cAllTicket>()
    call.respond(ticketservice.getAllTicket(cAllTicket))

    }

    post("/create") {
        val cticket = call.receive<cTicket>()
        call.respond(ticketservice.addTicket(cticket))
    }

    delete ("/delete"){
        val cDeleteTicket = call.receive<cDeleteTicket>()
        call.respond(ticketservice.deleteTicket(cDeleteTicket))
    }

    put ("/update"){
        val cUpdateTicket = call.receive<cUpdateTicket>()
        call.respond(ticketservice.updateTicket(cUpdateTicket))
    }

    put ("/move"){

    }
}