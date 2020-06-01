package ru.memeapp.echo.service

import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import kotlinx.coroutines.*
import ru.memeapp.echo.model.JokeType
import ru.memeapp.echo.repository.JokeRepository
import ru.memebattle.model.vk.model.VKResponse

class ParserService(
    private val gson: Gson,
    private val jokeRepository: JokeRepository,
    private val vkToken: String
) {

    init {
        GlobalScope.launch {
            startParser()
        }
    }

    private suspend fun startParser() {
        withContext(Dispatchers.Default) {
            while (true) {
                jokeRepository.deleteAll()

                val client = HttpClient(Apache)

                client.get<String>("https://memeapptest.herokuapp.com/api/v1/memes")
                client.get<String>("https://memebattle.herokuapp.com/api/v1/memes")

                val pulls = listOf(
                    JokeType.JOKE to listOf("149279263"),
                    JokeType.HUMORESQUE to listOf("92876084")
                )

                pulls.forEach { (type, groupIds) ->
                    groupIds.forEach { groupId ->
                        val response =
                            client.get<String>("https://api.vk.com/method/wall.get?owner_id=-$groupId&access_token=$vkToken&v=5.103&extended=1&count=100")
                        val vkResponse = gson.fromJson(response, VKResponse::class.java)

                        vkResponse.response?.items?.filter {
                            it.markedAsAds != 1
                        }?.filter {
                            it.attachments?.size ?: 0 == 0
                        }?.filter {
                            it.text?.length ?: 1025 <= 1024
                        }?.map {
                            it.text
                        }?.forEach {
                            if (it != null) {
                                jokeRepository.save(it, type)
                            }
                        }
                    }
                }

                delay(60000 * 30)
            }
        }
    }
}