package com.example.handlers


import com.example.model.callModel.cProject
import com.example.model.callModel.cTicket
import com.example.model.callModel.cTicketlist
import com.example.services.Projectservice
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement

suspend fun Controllers(callmodel : Any) : String
{
    val projectservice = Projectservice()
    var response =""

    if(callmodel is cProject)
    {

         projectservice.addProject(callmodel)
        return Json.encodeToString(projectservice.getAllProject())
    }else if (callmodel as String =="getAll")
    {
        println(callmodel as String)
        return Json.encodeToString(projectservice.getAllProject())
    }


    return response
}

/*

 is cTicketlist ->{

            Json.encodeToString(projectservice.getAllProject())
        }

        is cTicket ->{

            Json.encodeToString(projectservice.getAllProject())
        }
 */