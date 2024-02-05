package com.example.plugins

import com.example.database.connection.Connection
import com.example.routes.Projectroutes
import com.example.routes.Ticketlistroutes
import com.example.routes.Ticketroutes
import com.example.utils.RabbitMQFactory
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun Application.configureRouting() {

    routing {
        Projectroutes()
        Ticketlistroutes()
        Ticketroutes()
        RabbitMQFactory().defaultChannelAndQueue().listeningMessage()


        get("/") {
            lateinit var er : String
            runBlocking {
                Connection.getDatabase().listCollections().collect{
                    er = it.toString()
                }
            }
            call.respondText("Hello World! et database "+ er )
        }
    }
}
