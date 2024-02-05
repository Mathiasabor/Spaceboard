package com.example.routes

import com.example.handlers.Tickethandler
import io.ktor.server.routing.*

fun Route.Ticketroutes()
{
    route("/ticket")
    {
        this.Tickethandler()
    }
}