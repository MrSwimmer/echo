package ru.memeapp.echo

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    val server = embeddedServer(Netty) {
        routing {
            get("/lol") {
                call.respondText("Лол")
            }
        }
    }
    server.start(wait = true)
}

