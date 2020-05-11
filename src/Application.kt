package ru.memeapp.echo

import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.EngineMain
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    EngineMain.main(args)
    val server = embeddedServer(Netty) {

    }
    server.start()
}

fun Application.module() {
    install(Routing) {
        route("/api") {
            get("/lol") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }
        }
    }
}