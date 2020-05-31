package ru.memeapp.echo.service

import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import kotlinx.coroutines.*
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

                val groupIds = listOf("149279263")
                groupIds.forEach { groupId ->
                    val response =
                        client.get<String>("https://api.vk.com/method/wall.get?owner_id=-$groupId&access_token=$vkToken&v=5.103&extended=1&count=100")

                    println("vkResponse $response")

                    val vkResponse = gson.fromJson(response, VKResponse::class.java)

                    vkResponse.response?.items?.filter {
                        it.markedAsAds != 1
                    }?.filter {
                        it.attachments?.size == 0
                    }?.filter {
                        it.text?.length ?: 1025 <= 1024
                    }?.map {
                        it.text
                    }?.forEach {
                        if (it != null) {
                            jokeRepository.save(it)
                        }
                    }
                }

                delay(60000 * 30)
            }
        }
    }
}