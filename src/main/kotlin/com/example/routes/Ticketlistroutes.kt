package com.example.routes

import com.example.handlers.Projecthandler
import com.example.handlers.TicketlistHandler
import io.ktor.server.routing.*

fun Route.Ticketlistroutes()
{
    route("/ticketlist")
    {
        this.TicketlistHandler()
    }
}