package ru.memeapp.echo

import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.EngineMain
import io.ktor.server.netty.Netty
import model.AliceRequest
import model.AliceResponse
import model.Response

fun main(args: Array<String>) {
    EngineMain.main(args)
    val server = embeddedServer(Netty) {

    }
    server.start()
}

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }

    install(Routing) {
        route("/") {
            post {
                val aliceRequest = call.receive<AliceRequest>()
                val response = Response(text = "Пошел нахуй", end_session = false)
                val aliceResponse = AliceResponse(response, "1.0")
                call.respond(aliceResponse)
            }
        }
    }
}