package com.example.handlers

import com.example.database.connection.Connection
import com.example.model.Project

import com.example.model.callModel.cProject

import com.example.services.Projectservice
import com.example.utils.RabbitMQFactory
import com.rabbitmq.tools.jsonrpc.JsonRpcMapper
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.Projecthandler()
{
    val projectservice = Projectservice()
    get ("/all"){

        val list = projectservice.getAllProject()
        val jsonString = Json.encodeToString(list)
        RabbitMQFactory().sendMessage(jsonString.toByteArray())

        call.respond(projectservice.getAllProject())
    }

    post ("/create"){
        val projectTocreate = call.receive<cProject>()
        call.respond(projectservice.addProject(projectTocreate))
    }

    delete("/delete"){
        val projectid = call.parameters["projectid"]
        call.respond(projectservice.deleteProject(projectid!!))
    }

    put("/update")
    {
        val projectToUpdate = call.receive<Project>()
        call.respond(projectservice.updateProject(projectToUpdate))
    }

}





