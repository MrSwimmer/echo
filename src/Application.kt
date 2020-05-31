package ru.memeapp.echo

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.server.netty.EngineMain
import model.AliceRequest
import model.AliceResponse
import model.Response

fun main(args: Array<String>) {
    EngineMain.main(args)
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
                val aliceRequest = call.receive<String>()
                val request = Gson().fromJson(aliceRequest, AliceRequest::class.java)
                val response = Response(text = request.request.original_utterance, end_session = false)
                val aliceResponse = AliceResponse(response, "1.0")
                call.respond(aliceResponse)
            }
            get {
                call.respondText("get")
            }
        }
    }
}