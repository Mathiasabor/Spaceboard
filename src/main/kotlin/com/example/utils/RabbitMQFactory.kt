package com.example.utils

import com.example.config.config
import com.example.handlers.Controllers
import com.example.model.callModel.cProject
import com.example.services.Projectservice
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import io.ktor.utils.io.core.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.text.String
import kotlin.text.toByteArray

class RabbitMQFactory {
    private val connectionFactory : ConnectionFactory
    init {
        connectionFactory = ConnectionFactory()
        connectionFactory.username= config.RABBITMQ_USERNAME
        connectionFactory.password = config.RABBITMQ_PASSWORD
        connectionFactory.host = config.RABBITMQ_HOST
        connectionFactory.port =config.RABBITMQ_PORT
        connectionFactory.virtualHost =config.RABBITMQ_VIRTUALHOST
    }

    fun getConnectionFactory() : ConnectionFactory
    {
        return connectionFactory
    }

    fun defaultChannelAndQueue(): RabbitMQFactory {

        getConnectionFactory().newConnection().run {
            this.createChannel().apply{
                this.exchangeDeclare(config.EXCHANGE_NAME, "direct", true);
                this.queueDeclare(config.QUEUE_NAME, true, false, false, null);
                this.queueBind(config.QUEUE_NAME, config.EXCHANGE_NAME, config.ROUTING_KEY);
                this.close()
            }
            this.close()
        }

        return this
    }

    fun sendMessage(message : ByteArray)
    {
        getConnectionFactory().newConnection().run {
            this.createChannel().apply {
                this.basicPublish(config.EXCHANGE_NAME, config.ROUTING_KEY,null, message)
                this.close()
            }
            this.close()
        }
    }




    fun listeningMessage()
    {
        val conn =  getConnectionFactory().newConnection()

        val chann = conn.createChannel()
        val deliverCallback = DeliverCallback { consumerTag, delivery ->
        val  message = String(delivery.body)

            println(message)

            val reu = Json.decodeFromString<cProject>(message)

            sendMessage("est venu ${ Json.encodeToString(reu)}".toByteArray())

        }
        val cancelCallback = CancelCallback{consumerTag ->
            println(" canceld ....  $consumerTag")
        }

        chann.basicConsume(config.QUEUE_NAME,true, deliverCallback,cancelCallback)


    }




}