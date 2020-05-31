package ru.memeapp.echo

import di.KodeinBuilder
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.server.cio.EngineMain
import org.kodein.di.generic.instance
import org.kodein.di.ktor.KodeinFeature
import org.kodein.di.ktor.kodein
import routing.RoutingV1

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

    install(KodeinFeature) {
        KodeinBuilder(environment).setup(this)
    }

    install(Routing) {
        val routingV1 by kodein().instance<RoutingV1>()
        routingV1.setup(this)
    }
}