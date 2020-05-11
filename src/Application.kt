package ru.memeapp.echo

import io.ktor.application.*
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(Routing) {
        route("/") {
            get {
                call.respondText { "Lol" }
            }
        }
    }
}