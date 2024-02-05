package com.example.routes


import com.example.handlers.Projecthandler
import com.example.services.Projectservice
import com.example.utils.RabbitMQFactory
import io.ktor.server.routing.*

fun Route.Projectroutes()
{
    route("/project")
    {
        this.Projecthandler()
    }
}

