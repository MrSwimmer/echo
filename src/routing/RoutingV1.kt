package routing

import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import model.AliceRequest
import model.AliceResponse
import model.Response

class RoutingV1(
    private val gson: Gson
) {
    fun setup(configuration: Routing) {
        with(configuration) {
            route("/") {
                post {
                    val aliceRequest = call.receive<String>()
                    val request = gson.fromJson(aliceRequest, AliceRequest::class.java)
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
}