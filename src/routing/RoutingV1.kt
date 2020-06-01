package routing

import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import model.alice.AliceRequest
import model.alice.AliceResponse
import model.alice.Response
import ru.memeapp.echo.service.JokeService

class RoutingV1(
    private val gson: Gson,
    private val jokeService: JokeService
) {
    fun setup(configuration: Routing) {
        with(configuration) {
            route("/") {
                post {
                    val aliceRequest = call.receive<String>()
                    val request = gson.fromJson(aliceRequest, AliceRequest::class.java)

                    val endSession = goodBye(request)
                    val responseText = createResponseText(request)

                    val response = Response(text = responseText, end_session = endSession)
                    val aliceResponse = AliceResponse(response, "1.0")
                    call.respond(aliceResponse)
                }

                get {
                    call.respondText(jokeService.getAll().toString())
                }
            }
        }
    }

    private suspend fun createResponseText(request: AliceRequest) =
        when {
            newSession(request) -> {
                """
                    Привет! Ты попал в мир анекдотов и юморесок.
                    Чтобы послушать анекдот, скажи команду - расскажи анекдот,
                    чтобы послушать юмореску, скажи команду - расскажи юмореску,
                    чтобы выйти из мира анекдотов и юморесок, скажи команду - пока
                """.trimIndent()
            }

            requestJoke(request) -> {
                jokeService.getJoke()
            }

            requestHumoresque(request) -> {
                jokeService.getHumoresque()
            }

            else -> {
                """
                    Я такого не умею, давай что-нибудь попроще
                """.trimIndent()
            }
        }

    private fun goodBye(request: AliceRequest) =
        request.request.nlu.tokens.any { it.contains("пока") }

    private fun newSession(request: AliceRequest) =
        request.session.new

    private fun requestJoke(request: AliceRequest) =
        request.request.nlu.tokens.any { it.contains("анекдот") }

    private fun requestHumoresque(request: AliceRequest) =
        request.request.nlu.tokens.any { it.contains("юмореск") }
}